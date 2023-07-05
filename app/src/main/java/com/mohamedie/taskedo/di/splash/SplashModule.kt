package com.mohamedie.taskedo.di.splash

import com.mohamedie.taskedo.ui.landing.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}