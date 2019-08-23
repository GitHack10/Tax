package ru.dagdelo.business05.presentation.screens.auth.signup.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import kotlinx.android.synthetic.main.fragment_sign_up.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.di.global.nameds.SIGN_UP_FLOW
import ru.dagdelo.business05.domain.global.models.UserInfo
import ru.dagdelo.business05.presentation.global.base.FlowFragment
import ru.dagdelo.business05.presentation.global.dialogs.TwoActionDialog
import ru.dagdelo.business05.presentation.global.utils.*
import ru.dagdelo.business05.presentation.screens.auth.signup.mvp.SignUpPresenter
import ru.dagdelo.business05.presentation.screens.auth.signup.mvp.SignUpView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class SignUpFragment : FlowFragment(), SignUpView, HasSupportFragmentInjector,
    View.OnClickListener {
    override val layoutRes = R.layout.fragment_sign_up
    override val container = R.id.registration_container

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

    @SuppressLint("SimpleDateFormat")
    private val dateSendFormat = SimpleDateFormat("yyyy-MM-dd")
    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy")
    private var maskedPhone = ""
    private var birth = ""

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
        }
        init()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.doneButton -> presenter.onDoneClicked(collectUserData())
            R.id.birthEdit -> showDatePicker()
        }
    }

    override fun showProgress(show: Boolean) {
        doneButtonProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showDataError(message: String) {
        TwoActionDialog(
            titleText = message,
            textRightButton = "Повторить попытку",
            textLeftButton = "Отменить",
            buttonRightDialogClickListener = {
                emailEdit.requestFocus()
                showKeyboard()
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showError(message: String) {
        TwoActionDialog(
            titleText = message,
            textRightButton = "Повторить попытку",
            textLeftButton = "Отменить",
            buttonRightDialogClickListener = {
                presenter.onDoneClicked(collectUserData())
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    private fun collectUserData() = UserInfo(
        name = usernameEdit.text.toString(),
        email = emailEdit.text.toString(),
        phone = maskedPhone.regexPhone(),
        birth = birth,
        gender = if (maleRadioButton.isChecked) 1 else 0
    )

    @SuppressLint("SimpleDateFormat")
    private fun showDatePicker() {
        val editDateFormat = SimpleDateFormat("dd MM yyyy")
        val calendar = Calendar.getInstance()
        val day: Int?
        val month: Int?
        val year: Int?

        if (birthEdit.text.isEmpty()) {
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
        } else {
            val date = dateFormat.parse(birthEdit.text.toString())
            val userBirth = editDateFormat.format(date).split(" ")
            day = userBirth[0].toInt()
            month = userBirth[1].toInt() - 1
            year = userBirth[2].toInt()
        }
        val datePickerDialog = DatePickerDialog(
            context!!,
            R.style.CustomDatePickerDialog,
            DatePickerDialog.OnDateSetListener { _, yearUser, monthOfYear, dayOfMonth ->
                calendar.set(yearUser, monthOfYear, dayOfMonth)
                birthEdit.setText(dateFormat.format(calendar.timeInMillis))
                birth = dateSendFormat.format(calendar.timeInMillis)
            }, year, month, day
        )

        val minDate = Calendar.getInstance()
        minDate.add(Calendar.YEAR, -10)
        datePickerDialog.datePicker.maxDate = minDate.timeInMillis
        datePickerDialog.show()
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_sign_up), true)
        enterPhoneEdit.setText(maskedPhone)

        doneButton.setOnClickListener(this)
        birthEdit.setOnClickListener(this)

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(usernameEdit),
            RxTextView.textChanges(emailEdit),
            RxTextView.textChanges(birthEdit)
        ) { username, email, birth ->
            username.isNotBlank() && email.isNotBlank() && birth.isNotBlank()
        }
            .subscribeBy { doneButton.accessible(it) }
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance(maskedPhone: String) = SignUpFragment().withArgs {
            putString(MASKED_PHONE, maskedPhone)
        }

        private const val MASKED_PHONE = "masked_phone"
    }
}
