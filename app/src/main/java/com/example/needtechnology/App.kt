package com.example.needtechnology

import com.example.needtechnology.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/** Created by Kamil Abdulatipov on 22.06.2019. */

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}