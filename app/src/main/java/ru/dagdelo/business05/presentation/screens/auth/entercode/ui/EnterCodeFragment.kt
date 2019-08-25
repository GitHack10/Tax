package ru.dagdelo.business05.presentation.screens.auth.entercode.ui

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
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
import kotlinx.android.synthetic.main.fragment_enter_code.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.di.global.nameds.ENTER_CODE_FLOW
import ru.dagdelo.business05.presentation.global.base.FlowFragment
import ru.dagdelo.business05.presentation.global.dialogs.TwoActionDialog
import ru.dagdelo.business05.presentation.global.utils.*
import ru.dagdelo.business05.presentation.screens.auth.entercode.mvp.EnterCodePresenter
import ru.dagdelo.business05.presentation.screens.auth.entercode.mvp.EnterCodeView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class EnterCodeFragment : FlowFragment(),
    EnterCodeView, HasSupportFragmentInjector, View.OnClickListener {
    override val container = R.id.enter_code_container
    override val layoutRes = R.layout.fragment_enter_code

    @Inject
    @field:Named(ENTER_CODE_FLOW)
    override lateinit var navigatorHolder: NavigatorHolder

    lateinit var navigator: SupportAppNavigator

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: EnterCodePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private var maskedPhone = ""
    private var phone = ""

    private val timer = object : CountDownTimer(SMS_TIMER, TIMER_INTERVAL) {

        override fun onTick(millisUntilFinished: Long) {
            val progressSeconds = resources.getQuantityString(
                R.plurals.number_of_seconds,
                millisUntilFinished.toInt() / 1000,
                millisUntilFinished / 1000
            )
            val progressText = getString(R.string.enter_code_timer, progressSeconds)
            enterCodeCounterText.text = progressText
        }

        override fun onFinish() {
            sendSmsAgainText.run {
                enterCodeCounterText.visibility = View.INVISIBLE
                visibility = View.VISIBLE
                setOnClickListener(this@EnterCodeFragment)
            }
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        navigator = SupportAppNavigator(activity, childFragmentManager, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWhiteStyleWindow(view, activity!!)
        arguments?.run {
            maskedPhone = getString(MASKED_PHONE, "")
            phone = getString(PHONE, "")
        }
        init()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            sendSmsButton.id -> {
                hideKeyboard()
                presenter.sendSmsClicked(
                    phone = phone,
                    smsCode = enterCodeMaskedEdit.rawText,
                    maskedPhone = maskedPhone
                )
            }
            wrongPhoneText.id -> presenter.wrongPhoneClicked()
            sendSmsAgainText.id -> {
                sendSmsAgainText.visibility = View.GONE
                presenter.retrySmsRequest(phone)
            }
        }
    }

    override fun showProgress(show: Boolean) {
        sendSmsProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showRetrySmsProgress(show: Boolean) {
        retrySmsProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showError(message: String) {
        TwoActionDialog(
            titleText = message,
            textRightButton = getString(R.string.try_again),
            textLeftButton = getString(R.string.cancel),
            buttonRightDialogClickListener = { sendSmsButton.performClick() }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showAuthError(message: String) {
        TwoActionDialog(
            titleText = message,
            textRightButton = getString(R.string.try_again),
            textLeftButton = getString(R.string.cancel),
            buttonRightDialogClickListener = {
                enterCodeMaskedEdit.run {
                    text?.clear()
                    showKeyboard()
                }
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_enter_code), showNavIcon = true)
        phoneTextView.text = maskedPhone

        sendSmsButton.setOnClickListener(this)
        wrongPhoneText.setOnClickListener(this)
        sendSmsAgainText.setOnClickListener(this)

        enterCodeMaskedEdit.run {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (sendSmsButton.isClickable) sendSmsButton.performClick()
                    else hideKeyboard()
                }
                false
            }
        }

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(enterCodeMaskedEdit),
            RxTextView.textChanges(enterCodeMaskedEdit)
        ) { _, _ -> enterCodeMaskedEdit.rawText.length > 5 }
            .subscribeBy {
                sendSmsButton.accessible(it)
            }
    }

    override fun startTimer() {
        sendSmsAgainText.visibility = View.GONE
        enterCodeCounterText.visibility = View.VISIBLE
        timer.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {

        fun newInstance(maskedPhone: String, phone: String) = EnterCodeFragment().withArgs {
            putString(MASKED_PHONE, maskedPhone)
            putString(PHONE, phone)
        }

        private const val MASKED_PHONE = "masked_phone"
        private const val PHONE = "phone"
    }
}