package com.example.needtechnology.presentation.screens.auth.passwordinput.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.di.global.nameds.PASSWORD_INPUT_FLOW
import com.example.needtechnology.presentation.global.base.FlowFragment
import com.example.needtechnology.presentation.screens.auth.passwordinput.mvp.PasswordInputPresenter
import com.example.needtechnology.presentation.screens.auth.passwordinput.mvp.PasswordInputView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class PasswordInputFragment : FlowFragment(), PasswordInputView, HasSupportFragmentInjector {
    override val container = R.id.password_input_container
    override val layoutRes = R.layout.fragment_password_input

    @Inject
    @field:Named(PASSWORD_INPUT_FLOW)
    override lateinit var navigatorHolder: NavigatorHolder

    lateinit var navigator: SupportAppNavigator

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: PasswordInputPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        navigator = SupportAppNavigator(activity, childFragmentManager, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onBackPressed() = presenter.onBackPressed()
}
