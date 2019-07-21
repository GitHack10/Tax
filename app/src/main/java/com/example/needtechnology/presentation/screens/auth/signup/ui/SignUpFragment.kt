package com.example.needtechnology.presentation.screens.auth.signup.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.di.global.nameds.SIGN_UP_FLOW
import com.example.needtechnology.presentation.global.base.FlowFragment
import com.example.needtechnology.presentation.global.utils.setWhiteStyleWindow
import com.example.needtechnology.presentation.screens.auth.signup.mvp.SignUpPresenter
import com.example.needtechnology.presentation.screens.auth.signup.mvp.SignUpView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_sign_up.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class SignUpFragment : FlowFragment(), SignUpView, HasSupportFragmentInjector, View.OnClickListener {
    override val container = R.id.registration_container
    override val layoutRes = R.layout.fragment_sign_up

    @Inject
    @field:Named(SIGN_UP_FLOW)
    override lateinit var navigatorHolder: NavigatorHolder

    lateinit var navigator: SupportAppNavigator

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        navigator = SupportAppNavigator(activity, childFragmentManager, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWhiteStyleWindow(view, activity!!)
        init()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            Button_registrationFragment_done.id -> presenter.onDoneClicked()
        }
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_sign_up), true)
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onBackPressed() = presenter.onBackPressed()
}
