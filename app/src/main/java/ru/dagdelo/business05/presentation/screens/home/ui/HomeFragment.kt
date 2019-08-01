package ru.dagdelo.business05.presentation.screens.home.ui

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
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
import ru.dagdelo.business05.data.global.local.SharedPreferenceStorage
import ru.dagdelo.business05.presentation.global.base.BaseFragment
import ru.dagdelo.business05.presentation.global.dialogscreens.TwoActionAlertDialog
import ru.dagdelo.business05.presentation.global.utils.accessible
import ru.dagdelo.business05.presentation.screens.home.mvp.HomePresenter
import ru.dagdelo.business05.presentation.screens.home.mvp.HomeView
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

    private lateinit var prefs: SharedPreferences

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = context!!.getSharedPreferences(
            SharedPreferenceStorage.PREF_PROFILE,
            Context.MODE_PRIVATE
        )
        val qrScannedString = prefs.getString("QR_STRING", "") ?: ""
        if (qrScannedString.isNotEmpty()) presenter.getScannedString(qrScannedString)
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
            R.id.sendCheckButton -> presenter.prepareCheck(
                fpd = inputFP.text.toString(),
                fd = inputFD.text.toString(),
                fn = inputFN.text.toString()
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
        TwoActionAlertDialog(
            titleText = message,
            textRightButton = "Повторить попытку",
            textLeftButton = "Отменить",
            buttonRightDialogClickListener = {
                presenter.prepareCheck(
                    fpd = inputFP.text.toString(),
                    fd = inputFD.text.toString(),
                    fn = inputFN.text.toString()
                )
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showScanError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        inputFP.text.clear()
        inputFD.text.clear()
        inputFN.text.clear()

        prefs.edit().putString("QR_STRING", "").apply()
    }

    override fun showScannedData(
        fd: String,
        fpd: String,
        fn: String
    ) {
        inputFP.setText(fpd)
        inputFD.setText(fd)
        inputFN.setText(fn)
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_home))

        qrScannerTextView.setOnClickListener(this)
        sendCheckButton.setOnClickListener(this)

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(inputFP),
            RxTextView.textChanges(inputFD),
            RxTextView.textChanges(inputFN)
        ) { fd, fp, fn ->
            fd.isNotBlank() && fp.isNotBlank() && fn.isNotBlank()
        }
            .subscribeBy { sendCheckButton.accessible(it) }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        prefs.edit().putString("QR_STRING", "").apply()
    }
}
