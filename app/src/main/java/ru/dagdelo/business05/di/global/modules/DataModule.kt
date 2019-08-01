package ru.dagdelo.business05.di.global.modules

import dagger.Module
import dagger.Provides
import ru.dagdelo.business05.domain.global.models.UserInfo
import javax.inject.Singleton

/** Created by Kamil Abdulatipov on 22.06.2019. */

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideUserInfo() = UserInfo()
}