package com.example.needtechnology.presentation.screens.home.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.presentation.global.base.BaseFragment
import com.example.needtechnology.presentation.global.utils.accessible
import com.example.needtechnology.presentation.screens.home.mvp.HomePresenter
import com.example.needtechnology.presentation.screens.home.mvp.HomeView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, HasSupportFragmentInjector, View.OnClickListener {
    override val layoutRes = R.layout.fragment_home

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.qrScannerTextView -> showQrScanner()
            R.id.sendCheck -> collectCheck()
        }
    }

    private fun showQrScanner() {

    }

    private fun collectCheck() {
        Toast.makeText(context, "Чек зарегистрирован!", Toast.LENGTH_SHORT).show()
        inputFP.text.clear()
        inputFD.text.clear()
        inputFN.text.clear()
        inputDate.text.clear()
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_home))

        qrScannerTextView.setOnClickListener(this)
        sendCheck.setOnClickListener(this)

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(inputFP),
            RxTextView.textChanges(inputFD),
            RxTextView.textChanges(inputFN),
            RxTextView.textChanges(inputDate)
        ) { fd, fp, fn, date ->
            fd.isNotBlank() && fp.isNotBlank()
                && fn.isNotBlank() && date.isNotBlank()
        }
            .subscribeBy { sendCheck.accessible(it) }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
