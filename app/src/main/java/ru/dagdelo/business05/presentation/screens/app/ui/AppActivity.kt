package ru.dagdelo.business05.presentation.screens.app.ui

import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ru.dagdelo.business05.R
import ru.dagdelo.business05.presentation.global.base.BaseActivity
import ru.dagdelo.business05.presentation.global.utils.setGradientStyleWindow
import ru.dagdelo.business05.presentation.screens.app.mvp.AppPresenter
import ru.dagdelo.business05.presentation.screens.app.mvp.AppView
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
        presenter.saveDeviceId(Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID))
    }

    override fun onBackPressed() = currentFragment?.onBackPressed() ?: super.onBackPressed()
}
