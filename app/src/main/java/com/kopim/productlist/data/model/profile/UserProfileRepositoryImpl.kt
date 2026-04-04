package com.kopim.productlist.data.model.profile

import com.kopim.productlist.data.model.auth.LoginCredentials
import com.kopim.productlist.data.model.database.DatabaseConnectionInterface
import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.database.daos.UserProfileDao
import com.kopim.productlist.data.model.database.entities.UserProfileEntity
import com.kopim.productlist.data.model.network.apimodels.profile.ProfileResponseData
import com.kopim.productlist.data.model.network.connections.user.UserProfileNetworkConnectionInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserProfileRepositoryImpl(
    private val dao: UserProfileDao,
    private val network: UserProfileNetworkConnectionInterface,
    private val spm: SharedPreferencesManager,
    private val localDb: DatabaseConnectionInterface,
) : UserProfileRepository {

    private val _credentialsSignInEvents = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    override val credentialsSignInEvents: SharedFlow<Unit> = _credentialsSignInEvents.asSharedFlow()

    override val profileFlow: Flow<UserProfile?> =
        dao.observeProfile().map { it?.toUserProfile() }

    override suspend fun refreshFromServer(): Result<Unit> = withContext(Dispatchers.IO) {
        val resp = network.getUserProfile()
        when {
            resp == null -> Result.failure(IllegalStateException("Нет ответа сервера"))
            !resp.isSuccessful -> Result.failure(
                HttpStatusException(resp.code(), resp.message()),
            )
            resp.body() == null -> Result.failure(IllegalStateException("Пустое тело ответа профиля"))
            else -> {
                upsertFromResponse(resp.body()!!)
                Result.success(Unit)
            }
        }
    }

    override suspend fun updateDisplayName(name: String): Result<Unit> = withContext(Dispatchers.IO) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return@withContext Result.failure(IllegalArgumentException("Пустое имя"))
        val resp = network.updateUserProfile(displayName = trimmed, profileColor = null)
        applyProfileResponse(resp, "Не удалось обновить имя")
    }

    override suspend fun updateProfileColor(color: String): Result<Unit> = withContext(Dispatchers.IO) {
        val normalized = ProfileColorString.normalizeOrDefault(color)
        val resp = network.updateUserProfile(displayName = null, profileColor = normalized)
        applyProfileResponse(resp, "Не удалось обновить цвет профиля")
    }

    override suspend fun changePassword(newPassword: String): Result<Unit> = withContext(Dispatchers.IO) {
        if (newPassword.isBlank()) {
            return@withContext Result.failure(IllegalArgumentException("Пустой пароль"))
        }
        val resp = network.changeUserPassword(newPassword)
        when {
            resp == null -> Result.failure(IllegalStateException("Нет ответа сервера"))
            !resp.isSuccessful -> Result.failure(
                HttpStatusException(resp.code(), resp.message()),
            )
            else -> {
                spm.accountPasswordSessionKnown = true
                spm.storedAccountPassword = newPassword
                Result.success(Unit)
            }
        }
    }

    override suspend fun switchToAnotherProfile(): Result<Unit> = withContext(Dispatchers.IO) {
        dao.clear()
        spm.userToken = null
        spm.accountPasswordSessionKnown = false
        spm.storedAccountPassword = ""
        if (!network.relogin()) {
            return@withContext Result.failure(IllegalStateException("Не удалось войти под новым профилем"))
        }
        refreshFromServer()
    }

    override suspend fun signInWithCredentials(credentials: LoginCredentials): Result<Unit> =
        withContext(Dispatchers.IO) {
            val id = credentials.trimmedUserId
            if (id.isEmpty() || credentials.password.isBlank()) {
                return@withContext Result.failure(IllegalArgumentException("Введите ID и пароль"))
            }
            val previousToken = spm.userToken
            spm.userToken = null
            val resp = network.loginWithCredentials(id, credentials.password)
            when {
                resp == null -> {
                    spm.userToken = previousToken
                    Result.failure(IllegalStateException("Нет ответа сервера"))
                }
                !resp.isSuccessful -> {
                    spm.userToken = previousToken
                    Result.failure(HttpStatusException(resp.code(), resp.message()))
                }
                resp.body()?.token.isNullOrBlank() -> {
                    spm.userToken = previousToken
                    Result.failure(IllegalStateException("Пустой токен"))
                }
                else -> {
                    val token = resp.body()!!.token
                    localDb.clearAllSessionData()
                    spm.userToken = token
                    spm.accountPasswordSessionKnown = true
                    spm.storedAccountPassword = credentials.password
                    refreshFromServer().fold(
                        onSuccess = {
                            _credentialsSignInEvents.tryEmit(Unit)
                            Result.success(Unit)
                        },
                        onFailure = { Result.failure(it) },
                    )
                }
            }
        }

    private suspend fun upsertFromResponse(data: ProfileResponseData) {
        dao.upsert(data.toUserProfileEntity())
    }

    private suspend fun applyProfileResponse(
        resp: retrofit2.Response<ProfileResponseData>?,
        userMessage: String,
    ): Result<Unit> {
        if (resp == null) return Result.failure(IllegalStateException("Нет ответа сервера"))
        if (!resp.isSuccessful) {
            return Result.failure(HttpStatusException(resp.code(), resp.message()))
        }
        val body = resp.body()
        if (body != null) {
            upsertFromResponse(body)
            return Result.success(Unit)
        }
        return refreshFromServer().fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(Exception(userMessage, it)) },
        )
    }

    private fun UserProfileEntity.toUserProfile(): UserProfile =
        UserProfile(
            userId = userId,
            displayName = displayName,
            profileColor = profileColor,
        )
}

private class HttpStatusException(val code: Int, message: String?) :
    Exception("HTTP $code ${message.orEmpty()}".trim())
