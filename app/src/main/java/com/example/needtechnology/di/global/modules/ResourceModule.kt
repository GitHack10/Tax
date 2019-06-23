package com.example.needtechnology.di.global.modules

import com.example.needtechnology.domain.ResourceManager
import com.example.needtechnology.presentation.global.AndroidResourceManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ResourceModule {

    @Binds
    @Singleton
    fun bindAndroidRecourceManager(androidResourceManager: AndroidResourceManager): ResourceManager
}