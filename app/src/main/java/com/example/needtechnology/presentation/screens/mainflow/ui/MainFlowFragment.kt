package com.example.needtechnology.presentation.screens.mainflow.ui

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.presentation.global.base.FlowFragment
import com.example.needtechnology.presentation.screens.mainflow.mvp.MainFlowPresenter
import com.example.needtechnology.presentation.screens.mainflow.mvp.MainFlowView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainFlowFragment : FlowFragment(), MainFlowView, HasSupportFragmentInjector {
    override val container = R.id.main_flow_container
    override val layoutRes = R.layout.fragment_main_flow

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    override lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: MainFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    // ui
    private lateinit var bottomNavigationView: BottomNavigationView


    var selectedScreen = 1

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    selectedScreen = 1
                    presenter.homeTabClicked(selectedScreen)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_checklist -> {
                    selectedScreen = 2
                    presenter.checklistTabClicked(selectedScreen)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_news -> {
                    selectedScreen = 3
                    presenter.newsTabClicked(selectedScreen)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    selectedScreen = 4
                    presenter.profileTabClicked(selectedScreen)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }
}
