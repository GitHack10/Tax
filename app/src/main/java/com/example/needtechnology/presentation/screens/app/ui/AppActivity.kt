package com.example.needtechnology.presentation.screens.app.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.presentation.global.base.BaseActivity
import com.example.needtechnology.presentation.global.utils.setGradientStyleWindow
import com.example.needtechnology.presentation.screens.app.mvp.AppPresenter
import com.example.needtechnology.presentation.screens.app.mvp.AppView
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class AppActivity : BaseActivity(), AppView, HasSupportFragmentInjector {
    override val container = R.id.AppActivity_container

    @Inject
    override lateinit var navigatorHolder: NavigatorHolder

    override var navigator: Navigator? = object : SupportAppNavigator(this, container) {
        override fun activityBack() {
            super.activityBack()
        }
    }

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: AppPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setGradientStyleWindow(this)
    }

    override fun onBackPressed() = currentFragment?.onBackPressed() ?: super.onBackPressed()
}