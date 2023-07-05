package com.mohamedie.taskedo.di

import com.mohamedie.taskedo.data.user.UserManagerImpl
import com.mohamedie.taskedo.di.auth.authModule
import com.mohamedie.taskedo.di.splash.splashModule
import com.mohamedie.taskedo.domain.data.UserManager
import org.koin.dsl.module

val appModule = module {
    includes(firebaseModule)
    single<UserManager> { UserManagerImpl(get(), get(), get()) }
    includes(authModule)
    includes(splashModule)
}