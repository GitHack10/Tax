package ru.dagdelo.business05

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ru.dagdelo.business05.di.DaggerAppComponent

/** Created by Kamil Abdulatipov on 22.06.2019. */

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}