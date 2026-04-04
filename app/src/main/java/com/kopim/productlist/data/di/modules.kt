package com.kopim.productlist.data.di

import com.kopim.productlist.data.model.database.DatabaseConnection
import com.kopim.productlist.data.model.database.DatabaseConnectionInterface
import com.kopim.productlist.data.model.carts.EditListPortImpl
import com.kopim.productlist.data.model.carts.JoinListByCodePortImpl
import com.kopim.productlist.data.model.carts.ListCartMetadataPortImpl
import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.database.utils.AppDatabase
import com.kopim.productlist.data.model.database.utils.DatabaseProvider
import com.kopim.productlist.data.model.datasource.CartsDataSource
import com.kopim.productlist.data.model.datasource.CartsDataSourceInterface
import com.kopim.productlist.data.model.datasource.ListDataSource
import com.kopim.productlist.data.model.datasource.ListDataSourceInterface
import com.kopim.productlist.data.model.profile.UserProfileRepository
import com.kopim.productlist.data.model.profile.UserProfileRepositoryImpl
import com.kopim.productlist.data.model.network.connections.carts.CartsNetworkConnection
import com.kopim.productlist.data.model.network.connections.carts.CartsNetworkConnectionInterface
import com.kopim.productlist.data.model.network.connections.fcm.FcmNetworkConnection
import com.kopim.productlist.data.model.network.connections.fcm.FcmNetworkConnectionInterface
import com.kopim.productlist.data.model.network.connections.list.ListNetworkConnection
import com.kopim.productlist.data.model.network.connections.list.ListNetworkConnectionInterface
import com.kopim.productlist.data.model.network.connections.user.UserProfileNetworkConnection
import com.kopim.productlist.data.model.network.connections.user.UserProfileNetworkConnectionInterface
import com.kopim.productlist.data.model.network.networksettings.apiservices.CartsApiService
import com.kopim.productlist.data.model.network.networksettings.apiservices.ListApiService
import com.kopim.productlist.data.model.network.networksettings.OkHttpClientHelper
import com.kopim.productlist.data.model.network.networksettings.RetrofitHelper
import com.kopim.productlist.data.model.network.networksettings.apiservices.FcmApiService
import com.kopim.productlist.data.model.network.networksettings.apiservices.UserApiService
import com.kopim.productlist.data.mvvm.editlist.EditListPort
import com.kopim.productlist.data.mvvm.editlist.EditListViewModel
import com.kopim.productlist.data.mvvm.homefeed.HomeFeedViewModel
import com.kopim.productlist.data.mvvm.joinlist.JoinListByCodePort
import com.kopim.productlist.data.mvvm.joinlist.JoinListByCodeViewModel
import com.kopim.productlist.data.mvvm.loginaccount.LoginAccountViewModel
import com.kopim.productlist.data.mvvm.list.ListCartMetadataPort
import com.kopim.productlist.data.mvvm.list.ListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    model()
    vmPorts()
    viewmodel()
}

private fun Module.model(){
    single<SharedPreferencesManager> {
        SharedPreferencesManager(get())
    }
    single<ListDataSourceInterface> {
        ListDataSource(get(), get())
    }
    single<CartsNetworkConnectionInterface> {
        CartsNetworkConnection(get(), get())
    }
    single<CartsDataSourceInterface> {
        CartsDataSource(get(), get())
    }
    single<DatabaseConnectionInterface> {
        DatabaseConnection(get())
    }
    single<ListNetworkConnectionInterface> {
        ListNetworkConnection(get(), get())
    }
    single<FcmNetworkConnectionInterface> {
        FcmNetworkConnection(get(), get())
    }

    single<OkHttpClient> {
        OkHttpClientHelper().provideOkHttpClient(get())
    }
    single<Retrofit> {
        RetrofitHelper.getRetrofit(get())
    }
    single<ListApiService> {
        RetrofitHelper.getListApiService(get())
    }
    single<CartsApiService> {
        RetrofitHelper.getCartsApiService(get())
    }
    single<FcmApiService> {
        RetrofitHelper.getFcmApiService(get())
    }
    single<UserApiService> {
        RetrofitHelper.getUserApiService(get())
    }
    single<UserProfileNetworkConnectionInterface> {
        UserProfileNetworkConnection(get(), get())
    }
    single<UserProfileRepository> {
        UserProfileRepositoryImpl(
            get<AppDatabase>().userProfileDao(),
            get(),
            get(),
            get(),
        )
    }
    single<AppDatabase> {
        DatabaseProvider.getDatabase(get())
    }
}

private fun Module.vmPorts() {
    single<JoinListByCodePort> { JoinListByCodePortImpl(get()) }
    single<EditListPort> { EditListPortImpl(get(), get()) }
    single<ListCartMetadataPort> { ListCartMetadataPortImpl(get()) }
}

private fun Module.viewmodel() {
    viewModel<ListViewModel> {
        ListViewModel(get(), get())
    }
    viewModel<HomeFeedViewModel> {
        HomeFeedViewModel(get(), get(), get())
    }
    viewModel<JoinListByCodeViewModel> {
        JoinListByCodeViewModel(get())
    }
    viewModel<LoginAccountViewModel> {
        LoginAccountViewModel(get())
    }
    viewModel { (listId: Long) ->
        EditListViewModel(listId, get())
    }
}