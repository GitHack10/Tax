package ru.dagdelo.business05.presentation.screens.sendcomplaint.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import ru.dagdelo.business05.R
import ru.dagdelo.business05.di.global.nameds.SEND_COMPLAINT_FLOW
import ru.dagdelo.business05.presentation.global.base.FlowFragment
import ru.dagdelo.business05.presentation.global.utils.setWhiteStyleWindow
import ru.dagdelo.business05.presentation.screens.sendcomplaint.mvp.SendComplaintPresenter
import ru.dagdelo.business05.presentation.screens.sendcomplaint.mvp.SendComplaintView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named


class SendComplaintFragment : FlowFragment(), SendComplaintView, HasSupportFragmentInjector, View.OnClickListener {
    override val layoutRes = R.layout.fragment_send_complaint_test
    override val container = R.id.send_complaint_container

    @Inject
    @field:Named(SEND_COMPLAINT_FLOW)
    override lateinit var navigatorHolder: NavigatorHolder

    lateinit var navigator: SupportAppNavigator

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: SendComplaintPresenter

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

        }
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_send_complaint), showNavIcon = true)
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onBackPressed() = presenter.onBackPressed()
}