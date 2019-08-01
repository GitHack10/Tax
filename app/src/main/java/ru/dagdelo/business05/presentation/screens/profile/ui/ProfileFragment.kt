package ru.dagdelo.business05.presentation.screens.profile.ui


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
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.global.models.User
import ru.dagdelo.business05.presentation.global.base.BaseFragment
import ru.dagdelo.business05.presentation.global.dialogscreens.TwoActionAlertDialog
import ru.dagdelo.business05.presentation.global.utils.accessible
import ru.dagdelo.business05.presentation.screens.profile.mvp.ProfilePresenter
import ru.dagdelo.business05.presentation.screens.profile.mvp.ProfileView
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
        if (show) saveChangesProgress.visibility = View.VISIBLE
        else {
            saveChangesProgress.visibility = View.INVISIBLE
            saveChangesButton.accessible(show)
        }
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

    override fun showSaveError(message: String) {
        TwoActionAlertDialog(
            textLeftButton = getString(R.string.btn_cancel),
            textRightButton = getString(R.string.tryAgain),
            titleText = message,
            buttonRightDialogClickListener = {
                presenter.onSaveChangesClicked(usernameEdit.text.toString())
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
            presenter.onSaveChangesClicked(usernameEdit.text.toString())
        }

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(usernameEdit),
            RxTextView.textChanges(emailEdit)
        ) { username, email ->
            username.isNotBlank() && username.toString() != userInfo?.fullName
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
