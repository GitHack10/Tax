package com.example.needtechnology.presentation.screens.auth.passwordinput.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.presentation.global.base.FlowFragment
import com.example.needtechnology.presentation.screens.auth.passwordinput.mvp.PasswordInputPresenter
import com.example.needtechnology.presentation.screens.auth.passwordinput.mvp.PasswordInputView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class PasswordInputFragment : FlowFragment(), PasswordInputView, HasSupportFragmentInjector {
    override val container = R.id.password_input_container
    override val layoutRes = R.layout.fragment_password_input

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    override lateinit var navigatorHolder: NavigatorHolder

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
