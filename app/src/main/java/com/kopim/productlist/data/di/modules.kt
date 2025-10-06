package com.kopim.productlist.data.di

import com.kopim.productlist.data.model.database.DatabaseConnection
import com.kopim.productlist.data.model.database.DatabaseConnectionInterface
import com.kopim.productlist.data.model.database.SharedPreferencesManager
import com.kopim.productlist.data.model.database.utils.AppDatabase
import com.kopim.productlist.data.model.database.utils.DatabaseProvider
import com.kopim.productlist.data.model.datasource.ListDataSource
import com.kopim.productlist.data.model.datasource.ListDataSourceInterface
import com.kopim.productlist.data.model.network.ListNetworkConnection
import com.kopim.productlist.data.model.network.ListNetworkConnectionInterface
import com.kopim.productlist.data.model.network.networksettings.ApiService
import com.kopim.productlist.data.model.network.networksettings.OkHttpClientHelper
import com.kopim.productlist.data.model.network.networksettings.RetrofitHelper
import com.kopim.productlist.data.mvvm.ListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    model()
    viewmodel()
}

private fun Module.model(){
    single<SharedPreferencesManager> {
        SharedPreferencesManager(get())
    }
    single<ListDataSourceInterface> {
        ListDataSource(get(), get())
    }
    single<DatabaseConnectionInterface> {
        DatabaseConnection(get())
    }
    single<ListNetworkConnectionInterface> {
        ListNetworkConnection(get(), get())
    }

    single<OkHttpClient> {
        OkHttpClientHelper().provideOkHttpClient(get())
    }
    single<Retrofit> {
        RetrofitHelper.getRetrofit(get())
    }
    single<ApiService> {
        RetrofitHelper.getApiService(get())
    }
    single<AppDatabase> {
        DatabaseProvider.getDatabase(get())
    }
}

private fun Module.viewmodel() {
    viewModel<ListViewModel> {
        ListViewModel(get())
    }
}