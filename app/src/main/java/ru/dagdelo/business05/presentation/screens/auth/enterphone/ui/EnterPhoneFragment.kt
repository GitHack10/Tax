package ru.dagdelo.business05.presentation.screens.auth.enterphone.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_enter_phone.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.di.global.nameds.ENTER_PHONE_FLOW
import ru.dagdelo.business05.presentation.global.base.FlowFragment
import ru.dagdelo.business05.presentation.global.dialogs.TwoActionDialog
import ru.dagdelo.business05.presentation.global.utils.accessible
import ru.dagdelo.business05.presentation.global.utils.hideKeyboard
import ru.dagdelo.business05.presentation.global.utils.setWhiteStyleWindow
import ru.dagdelo.business05.presentation.global.utils.showKeyboard
import ru.dagdelo.business05.presentation.screens.auth.enterphone.mvp.EnterPhonePresenter
import ru.dagdelo.business05.presentation.screens.auth.enterphone.mvp.EnterPhoneView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class EnterPhoneFragment : FlowFragment(),
    EnterPhoneView, HasSupportFragmentInjector, View.OnClickListener {
    override val container = R.id.enter_phone_container
    override val layoutRes = R.layout.fragment_enter_phone

    @Inject
    @field:Named(ENTER_PHONE_FLOW)
    override lateinit var navigatorHolder: NavigatorHolder

    lateinit var navigator: SupportAppNavigator

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: EnterPhonePresenter

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
            sendPhoneButton.id -> {
                hideKeyboard()
                presenter.onNextClicked(
                    maskedPhone = enterPhoneMaskedEdit.text.toString(),
                    phone = enterPhoneMaskedEdit.rawText
                )
            }
        }
    }

    override fun showProgress(show: Boolean) {
        sendPhoneProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showTimeOutError(message: String) {
        TwoActionDialog(
            titleText = message,
            textRightButton = getString(R.string.enter_phone_next),
            textLeftButton = getString(R.string.cancel),
            buttonRightDialogClickListener = {
                presenter.onErrorNextClicked(
                    maskedPhone = enterPhoneMaskedEdit.text.toString(),
                    phone = enterPhoneMaskedEdit.rawText
                )
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showError(message: String) {
        TwoActionDialog(
            titleText = message,
            textRightButton = getString(R.string.try_again),
            textLeftButton = getString(R.string.cancel),
            buttonRightDialogClickListener = { sendPhoneButton.performClick() }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_enter_phone))
        sendPhoneButton.setOnClickListener(this)

        enterPhoneMaskedEdit.run {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (sendPhoneButton.isClickable) sendPhoneButton.performClick()
                    else hideKeyboard()
                }
                false
            }
        }

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(enterPhoneMaskedEdit),
            RxTextView.textChanges(enterPhoneMaskedEdit)
        ) { _, _ -> enterPhoneMaskedEdit.rawText.length > 9 }
            .subscribeBy {
                sendPhoneButton.accessible(it)
            }
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onBackPressed() = presenter.onBackPressed()
}