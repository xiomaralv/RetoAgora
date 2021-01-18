package com.intercorp.retoagora

import com.intercorp.retoagora.data.ApiService
import com.intercorp.retoagora.data.repository.RetoAgoraRepository
import com.intercorp.retoagora.ui.people.list.PeopleViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {

    single { RetoAgoraRepository(get()) }

    single { createWebService() }

    viewModel { PeopleViewModel(get()) }

}

fun createWebService(): ApiService {
    val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://swapi.dev/api/")
            .build()

    return retrofit.create(ApiService::class.java)
}