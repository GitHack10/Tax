package ru.dagdelo.business05.di.global.modules

import dagger.Binds
import dagger.Module
import ru.dagdelo.business05.domain.ResourceManager
import ru.dagdelo.business05.presentation.global.AndroidResourceManager
import javax.inject.Singleton

@Module
interface ResourceModule {

    @Binds
    @Singleton
    fun bindAndroidRecourceManager(androidResourceManager: AndroidResourceManager): ResourceManager
}