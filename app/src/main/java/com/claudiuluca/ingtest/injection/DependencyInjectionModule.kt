package com.claudiuluca.ingtest.injection

import com.claudiuluca.ingtest.BuildConfig
import com.claudiuluca.ingtest.data.IngTestRepository
import com.claudiuluca.ingtest.data.RepositoryInterface
import com.claudiuluca.ingtest.data.api.NetworkApiService
import com.claudiuluca.ingtest.data.api.NetworkFactory
import com.claudiuluca.ingtest.data.api.NetworkInterface
import com.claudiuluca.ingtest.data.api.NetworkRepository
import com.claudiuluca.ingtest.ui.main.MainViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object NetworkApi {
    val networkApiService: NetworkApiService by lazy {
        val networkFactory = NetworkFactory(moshi)
        networkFactory.createApi(NetworkApiService::class.java, BuildConfig.ALL_CAMPAIGNS_URL)
    }
}

val remoteDatasourceModule = module(createdAtStart = true) {
    single { moshi }

    single { NetworkApi }

    single<NetworkInterface> { NetworkRepository(get()) }
}

val repositoryModule = module(createdAtStart = true) {
    single<RepositoryInterface> { IngTestRepository(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val modulesList = listOf(
    remoteDatasourceModule,
    repositoryModule,
    mainModule
)