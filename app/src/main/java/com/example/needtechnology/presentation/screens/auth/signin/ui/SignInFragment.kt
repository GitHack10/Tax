package com.example.needtechnology.presentation.screens.auth.signin.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.di.global.nameds.ENTER_PHONE_FLOW
import com.example.needtechnology.presentation.global.base.FlowFragment
import com.example.needtechnology.presentation.global.dialogscreens.TwoActionAlertDialog
import com.example.needtechnology.presentation.global.utils.accessible
import com.example.needtechnology.presentation.global.utils.setWhiteStyleWindow
import com.example.needtechnology.presentation.screens.auth.signin.mvp.SignInPresenter
import com.example.needtechnology.presentation.screens.auth.signin.mvp.SignInView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_enter_phone.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class SignInFragment : FlowFragment(),
    SignInView, HasSupportFragmentInjector, View.OnClickListener {
    override val container = R.id.auth_container
    override val layoutRes = R.layout.fragment_sign_in

    @Inject
    @field:Named(ENTER_PHONE_FLOW)
    override lateinit var navigatorHolder: NavigatorHolder

    lateinit var navigator: SupportAppNavigator

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: SignInPresenter

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
            signInButton.id -> presenter.signInClicked(
                email = emailEdit.text.toString(),
                password = passwordEdit.text.toString()
            )
            registrationText.id -> presenter.onRegistrationClicked()
        }
    }

    override fun showProgress(show: Boolean) {
        signInProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showAuthError(message: String) {
        errorText.text = message
        errorText.visibility = View.VISIBLE
    }

    override fun showError(message: String) {
        TwoActionAlertDialog(
            titleText = message,
            textRightButton = "Повторить попытку",
            textLeftButton = "Отменить",
            buttonRightDialogClickListener = {
                presenter.signInClicked(phoneEdit.rawText, phoneEdit.text.toString())
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_sign_in))
        signInButton.setOnClickListener(this)
        registrationText.setOnClickListener(this)

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(emailEdit),
            RxTextView.textChanges(passwordEdit)
        ) { email, password -> email.isNotBlank() && password.isNotBlank() }
            .subscribeBy {
                errorText.visibility = View.GONE
                signInButton.accessible(it)
            }
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onBackPressed() = presenter.onBackPressed()
}
