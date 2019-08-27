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
import kotlinx.android.synthetic.main.no_network.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.global.models.User
import ru.dagdelo.business05.presentation.global.base.BaseFragment
import ru.dagdelo.business05.presentation.global.dialogs.TwoActionDialog
import ru.dagdelo.business05.presentation.global.utils.accessible
import ru.dagdelo.business05.presentation.screens.profile.mvp.ProfilePresenter
import ru.dagdelo.business05.presentation.screens.profile.mvp.ProfileView
import javax.inject.Inject

class ProfileFragment : BaseFragment(), ProfileView, HasSupportFragmentInjector, View.OnClickListener {
    override val layoutRes = R.layout.fragment_profile

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private var user: User? = null

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
            R.id.saveChangesButton -> {
                presenter.onSaveChangesClicked(
                    username = usernameEdit.text.toString(),
                    email = emailEdit.text.toString()
                )
            }
            R.id.networkCheckButton -> presenter.onTryAgainClicked()
        }
    }

    override fun showLoadProgress(show: Boolean) {
        loadProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showSaveProgress(show: Boolean) {
        saveChangesProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
        saveChangesButton.accessible(show)
    }

    override fun showContentLayout(show: Boolean) {
        profileScrollView.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showError(message: String) {
        TwoActionDialog(
            textLeftButton = getString(R.string.btn_cancel),
            textRightButton = getString(R.string.try_again),
            titleText = message,
            buttonRightDialogClickListener = {
                presenter.onTryAgainClicked()
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showNoNetworkLayout(show: Boolean) {
        noNetworkLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showSaveError(message: String) {
        TwoActionDialog(
            textLeftButton = getString(R.string.btn_cancel),
            textRightButton = getString(R.string.try_again),
            titleText = message,
            buttonRightDialogClickListener = {
                presenter.onSaveChangesClicked(
                    username = usernameEdit.text.toString(),
                    email = emailEdit.text.toString()
                )
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showUserInfo(userInfo: User) {
        user = userInfo
        user?.let {
            usernameEdit.setText(user?.fullName)
            emailEdit.setText(user?.email)
            enterPhoneMaskedEdit.setText(user?.phone?.removeRange(0, 1))
            birthEdit.setText(user?.birth)
            genderEdit.setText(user?.gender)
        } ?: showError(resources.getString(R.string.error_load_data))
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_profile))
        setupToolbarMenu()

        saveChangesButton.setOnClickListener(this)
        networkCheckButton.setOnClickListener(this)

        subscriptions += Observables.combineLatest(
            RxTextView.textChanges(usernameEdit),
            RxTextView.textChanges(emailEdit)
        ) { username, email ->
            username.isNotBlank() && username.toString() != user?.fullName
                || email.toString() != user?.email
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
        TwoActionDialog(
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
