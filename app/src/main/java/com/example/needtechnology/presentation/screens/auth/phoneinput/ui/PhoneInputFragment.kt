package com.example.needtechnology.presentation.screens.auth.phoneinput.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.presentation.screens.auth.phoneinput.mvp.PhoneInputView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import com.example.needtechnology.presentation.global.base.FlowFragment
import com.example.needtechnology.presentation.global.utils.setWhiteStyleWindow
import com.example.needtechnology.presentation.screens.auth.phoneinput.mvp.PhoneInputPresenter
import dagger.android.support.AndroidSupportInjection
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class PhoneInputFragment : FlowFragment(), PhoneInputView, HasSupportFragmentInjector {
    override val container = R.id.phone_input_container
    override val layoutRes = R.layout.fragment_phone_input

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    override lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: PhoneInputPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWhiteStyleWindow(view, activity!!)
        initViews()
    }

    private fun initViews() {

    }
}
