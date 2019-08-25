package ru.dagdelo.business05.presentation.screens.sendcomplaint.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_send_complaint_test.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.di.global.nameds.SEND_COMPLAINT_FLOW
import ru.dagdelo.business05.domain.global.models.Complaint
import ru.dagdelo.business05.presentation.global.base.FlowFragment
import ru.dagdelo.business05.presentation.global.dialogs.OneActionDialog
import ru.dagdelo.business05.presentation.global.dialogs.TwoActionDialog
import ru.dagdelo.business05.presentation.global.utils.accessible
import ru.dagdelo.business05.presentation.global.utils.setWhiteStyleWindow
import ru.dagdelo.business05.presentation.screens.sendcomplaint.mvp.SendComplaintPresenter
import ru.dagdelo.business05.presentation.screens.sendcomplaint.mvp.SendComplaintView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class SendComplaintFragment : FlowFragment(), SendComplaintView, HasSupportFragmentInjector,
    View.OnClickListener {
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

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy' 'HH:mm")
    private var date = ""

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
            R.id.sendComplaintButton -> presenter.onSendComplaintClicked(collectComplaintData())
            R.id.inputDateEdit -> showDatePicker()
        }
    }

    override fun showProgress(show: Boolean) {
        sendComplaintProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showError(message: String) {
        TwoActionDialog(
            titleText = message,
            textRightButton = getString(R.string.try_again),
            textLeftButton = getString(R.string.cancel),
            buttonRightDialogClickListener = { sendComplaintButton.performClick() }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showComplete(title: String, message: String) {
        OneActionDialog(
            titleText = title,
            descText = message,
            textButton = getString(R.string.send_complaint_alert_button)
        ).show(fragmentManager, "OneActionDialog.javaClass.simpleName")
        clearInputData()
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_send_complaint), showNavIcon = true)

        sendComplaintButton.setOnClickListener(this)
        inputDateEdit.setOnClickListener(this)

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(inputProblemEdit),
            RxTextView.textChanges(inputAddressEdit),
            RxTextView.textChanges(inputDateEdit)
        ) { problemDesc, address, date ->
            problemDesc.isNotBlank() && address.isNotBlank() && date.isNotBlank()
        }
            .subscribeBy { sendComplaintButton.accessible(it) }
    }

    private fun clearInputData() {
        inputProblemEdit.text.clear()
        inputAddressEdit.text.clear()
        inputDateEdit.text.clear()
        inputProblemEdit.requestFocus()
    }

    private fun collectComplaintData() =
        Complaint(
            problemDesc = inputProblemEdit.text.toString(),
            address = inputAddressEdit.text.toString(),
            date = inputDateEdit.text.toString()
        )

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val day: Int?
        val month: Int?
        val year: Int?
        var time = ""

        if (inputDateEdit.text.isEmpty()) {
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
        } else {
            val userDate = inputDateEdit.text.toString().split(".")
            day = userDate[0].toInt()
            month = userDate[1].toInt() - 1
            year = userDate[2].split(" ")[0].toInt()
            time = userDate[2].split(" ")[1]
        }
        val datePickerDialog = DatePickerDialog(
            context!!,
            R.style.CustomDatePickerDialog,
            DatePickerDialog.OnDateSetListener { _, yearUser, monthOfYear, dayOfMonth ->
                calendar.set(yearUser, monthOfYear, dayOfMonth)
                showTimePicker(yearUser, monthOfYear, dayOfMonth, time)
            }, year, month, day
        )

        datePickerDialog.show()
    }

    private fun showTimePicker(yearUser: Int, monthOfYear: Int, dayOfMonth: Int, time: String) {
        val calendar = Calendar.getInstance()
        val hour: Int?
        val minute: Int?

        if (time.isEmpty()) {
            hour = calendar.get(Calendar.DAY_OF_MONTH)
            minute = calendar.get(Calendar.MONTH)
        } else {
            val userTime = time.split(":")
            hour = userTime[0].toInt()
            minute = userTime[1].toInt()
        }
        val timePickerDialog = TimePickerDialog(
            context!!,
            R.style.CustomDatePickerDialog,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minutes ->
                calendar.set(yearUser, monthOfYear, dayOfMonth, hourOfDay, minutes)
                date = dateFormat.format(calendar.timeInMillis)
                inputDateEdit.setText(date)
            }, hour, minute, true
        )

        timePickerDialog.show()
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onBackPressed() = presenter.onBackPressed()
}