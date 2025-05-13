package com.rafaels.marsrover.di

import com.rafaels.data.RestClient
import com.rafaels.data.error.ErrorHandler
import com.rafaels.data.repository.MarsRoverRepositoryImpl
import com.rafaels.data.repository.MockMarsRoverRepositoryImpl
import com.rafaels.domain.repository.MarsRoverRepository
import com.rafaels.domain.usecase.LoadMarsRoverDataUseCase
import com.rafaels.marsrover.BuildConfig
import com.rafaels.marsrover.ui.feature.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}

val domainModule = module {
    single { LoadMarsRoverDataUseCase(get()) }
}

val dataModule = module {
    single<MarsRoverRepository> {
        if (BuildConfig.IS_DEMO) MockMarsRoverRepositoryImpl(get())
        else MarsRoverRepositoryImpl(get(), get())
    }
    single { RestClient.getMarsRoverApi() }
    single { ErrorHandler() }
}