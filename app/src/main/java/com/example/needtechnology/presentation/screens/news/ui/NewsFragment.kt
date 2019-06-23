package com.example.needtechnology.presentation.screens.news.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.presentation.global.base.BaseFragment
import com.example.needtechnology.presentation.screens.news.mvp.NewsPresenter
import com.example.needtechnology.presentation.screens.news.mvp.NewsView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class NewsFragment : BaseFragment(), NewsView, HasSupportFragmentInjector {
    override val layoutRes = R.layout.fragment_news

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: NewsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

    }
}
