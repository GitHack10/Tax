package com.example.needtechnology.presentation.screens.profile.ui


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.domain.global.models.User
import com.example.needtechnology.domain.global.models.UserInfo
import com.example.needtechnology.presentation.global.base.BaseFragment
import com.example.needtechnology.presentation.global.dialogscreens.TwoActionAlertDialog
import com.example.needtechnology.presentation.global.utils.accessible
import com.example.needtechnology.presentation.screens.profile.mvp.ProfilePresenter
import com.example.needtechnology.presentation.screens.profile.mvp.ProfileView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ProfileFragment : BaseFragment(), ProfileView, HasSupportFragmentInjector {
    override val layoutRes = R.layout.fragment_profile

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private var userInfo: User? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun showLoadProgress(show: Boolean) {
        if (show) {
            profileScrollView.visibility = View.INVISIBLE
            loadProgress.visibility = View.VISIBLE
        } else {
            profileScrollView.visibility = View.VISIBLE
            loadProgress.visibility = View.INVISIBLE
        }
    }

    override fun showSaveProgress(show: Boolean) {
        saveChangesProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showError(message: String) {
        TwoActionAlertDialog(
            textLeftButton = getString(R.string.btn_cancel),
            textRightButton = getString(R.string.tryAgain),
            titleText = message,
            buttonRightDialogClickListener = {
                presenter.getUserInfo()
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showUserInfo(userInfo: User) {
        this.userInfo = userInfo.copy()
        usernameEdit.setText(userInfo.fullName)
        emailEdit.setText(userInfo.email)
        phoneEdit.setText(userInfo.phone.removeRange(0, 1))
        birthEdit.setText(userInfo.birth)
        genderEdit.setText(userInfo.gender)
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_profile))
        setupToolbarMenu()

        saveChangesButton.setOnClickListener{
            presenter.onSaveChangesClicked(usernameEdit.text.toString(), emailEdit.text.toString())
            Toast.makeText(context, "Данные сохранены", Toast.LENGTH_SHORT).show()
        }

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(usernameEdit),
            RxTextView.textChanges(emailEdit)
        ) { username, email ->
            username.isNotBlank() && email.isNotBlank() && username.toString() != userInfo?.fullName
                || username.isNotBlank() && email.isNotBlank() && email.toString() != userInfo?.email
        }
            .subscribeBy { saveChangesButton.accessible(it) }
    }

    private fun setupToolbarMenu() {
        menuIconPlaceholder.visibility = View.GONE
        toolbar.run {
            // Не добавляет меню, если уже имеется
            if (menu.size() <= 0) {
                inflateMenu(R.menu.logout_profle)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_profile_logout -> {
                            showLogoutAlert()
                            true
                        }

                        else -> false
                    }
                }
            }
        }
    }

    private fun showLogoutAlert() {
        TwoActionAlertDialog(
            textLeftButton = getString(R.string.btn_cancel),
            textRightButton = getString(R.string.btn_yes),
            titleText = getString(R.string.exit_dialog_text),
            buttonRightDialogClickListener = {
                presenter.onLogoutClicked()
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
