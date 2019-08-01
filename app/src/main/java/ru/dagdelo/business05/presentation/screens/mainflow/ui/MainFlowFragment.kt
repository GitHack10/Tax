package ru.dagdelo.business05.presentation.screens.mainflow.ui

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.di.global.nameds.MAIN_FLOW
import ru.dagdelo.business05.presentation.global.base.FlowFragment
import ru.dagdelo.business05.presentation.global.utils.setWhiteStyleWindow
import ru.dagdelo.business05.presentation.screens.mainflow.mvp.MainFlowPresenter
import ru.dagdelo.business05.presentation.screens.mainflow.mvp.MainFlowView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class MainFlowFragment : FlowFragment(), MainFlowView, HasSupportFragmentInjector {
    override val container = R.id.main_flow_container
    override val layoutRes = R.layout.fragment_main_flow

    @Inject
    @field:Named(MAIN_FLOW)
    override lateinit var navigatorHolder: NavigatorHolder

    lateinit var navigator: SupportAppNavigator

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: MainFlowPresenter

    @ProvidePresenter fun providePresenter() = presenter

    // ui
    private lateinit var bottomNavigationView: BottomNavigationView


    var selectedScreen = 1

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        navigator = SupportAppNavigator(activity, childFragmentManager, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWhiteStyleWindow(view, activity!!)
        initBottomView()
    }

    override fun highlightTab(selectedScreen: Int) {
        bottomNavigationView.selectedItemId = selectedScreen
    }

    private fun initBottomView() {
        bottomNavigationView = this.BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    presenter.homeTabClicked(selectedScreen)
                    selectedScreen = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_checklist -> {
                    presenter.checklistTabClicked(selectedScreen)
                    selectedScreen = 2
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_news -> {
                    presenter.newsTabClicked(selectedScreen)
                    selectedScreen = 3
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    presenter.profileTabClicked(selectedScreen)
                    selectedScreen = 4
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onBackPressed() = currentFragment?.onBackPressed() ?: presenter.onBackPressed()
}
