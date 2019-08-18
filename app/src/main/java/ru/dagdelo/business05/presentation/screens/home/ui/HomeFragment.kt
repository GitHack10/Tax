package ru.dagdelo.business05.presentation.screens.home.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_home.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.global.models.Check
import ru.dagdelo.business05.presentation.global.base.BaseFragment
import ru.dagdelo.business05.presentation.global.dialogs.OneActionDialog
import ru.dagdelo.business05.presentation.global.dialogs.TwoActionDialog
import ru.dagdelo.business05.presentation.global.utils.accessible
import ru.dagdelo.business05.presentation.screens.home.mvp.HomePresenter
import ru.dagdelo.business05.presentation.screens.home.mvp.HomeView
import java.text.SimpleDateFormat
import java.util.*
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

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy' 'HH:mm")

    private var dateSend = ""
    private var date = ""

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getScannedString()
        init()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.qrScannerTextView -> {
                val rxPermissions = RxPermissions(this)
                rxPermissions
                    .request(Manifest.permission.CAMERA)
                    .subscribeBy(
                        onNext = { presenter.onQrScannerClicked() },
                        onError = { }
                    )
            }
            R.id.inputDate -> showDatePicker()
            R.id.sendCheckButton -> presenter.prepareCheck(
                Check(
                    fpd = inputFP.text.toString(),
                    fd = inputFD.text.toString(),
                    fn = inputFN.text.toString(),
                    date = inputDate.text.toString(),
                    type = spinner.selectedItemPosition + 1,
                    sum = inputSum.text.toString()
                )
            )
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(show: Boolean) {
        prepareCheckProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showError(message: String) {
        TwoActionDialog(
            titleText = message,
            textRightButton = "Повторить попытку",
            textLeftButton = "Отменить",
            buttonRightDialogClickListener = {
                presenter.prepareCheck(
                    Check(
                        fpd = inputFP.text.toString(),
                        fd = inputFD.text.toString(),
                        fn = inputFN.text.toString(),
                        date = inputDate.text.toString(),
                        type = spinner.selectedItemPosition + 1,
                        sum = inputSum.text.toString()
                    )
                )
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showScanError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(title: String, desc: String, positiveText: String) {
        OneActionDialog(
            titleText = title,
            descText = desc,
            textButton = positiveText
        ).show(fragmentManager, "OneActionDialog.javaClass.simpleName")

        inputFP.text.clear()
        inputFD.text.clear()
        inputFN.text.clear()
        inputDate.text.clear()
        inputSum.text.clear()
        spinner.isSelected = false
    }

    override fun showScannedData(check: Check?) {
        inputFP.setText(check?.fpd)
        inputFD.setText(check?.fd)
        inputFN.setText(check?.fn)
        inputDate.setText(check?.date)
        inputSum.setText(check?.sum)
        spinner.setSelection((check?.type ?: 1) - 1)
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_home))

        qrScannerTextView.setOnClickListener(this)
        inputDate.setOnClickListener(this)
        sendCheckButton.setOnClickListener(this)

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(inputFP),
            RxTextView.textChanges(inputFD),
            RxTextView.textChanges(inputFN),
            RxTextView.textChanges(inputDate),
            RxTextView.textChanges(inputSum)
        ) { fd, fp, fn, date, sum ->
            fd.isNotBlank() && fp.isNotBlank() && fn.isNotBlank() && date.isNotBlank() && sum.isNotBlank()
        }
            .subscribeBy { sendCheckButton.accessible(it) }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val day: Int?
        val month: Int?
        val year: Int?
        var time = ""

        if (inputDate.text.isEmpty()) {
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
        } else {
            val userDate = inputDate.text.toString().split(".")
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
                inputDate.setText(date)
            }, hour, minute, true
        )

        timePickerDialog.show()
    }

    override fun onBackPressed() = presenter.onBackPressed()
}
