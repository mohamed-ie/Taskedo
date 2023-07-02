package com.mohamedie.taskedo.di

import com.mohamedie.taskedo.di.auth.authModule
import org.koin.dsl.module

val appModule = module {
    includes(authModule)
}