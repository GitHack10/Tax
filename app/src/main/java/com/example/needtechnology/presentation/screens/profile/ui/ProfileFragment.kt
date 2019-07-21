package com.example.needtechnology.presentation.screens.profile.ui


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.needtechnology.R
import com.example.needtechnology.domain.global.UserInfo
import com.example.needtechnology.presentation.global.base.BaseFragment
import com.example.needtechnology.presentation.global.dialogscreens.TwoActionAlertDialog
import com.example.needtechnology.presentation.screens.profile.mvp.ProfilePresenter
import com.example.needtechnology.presentation.screens.profile.mvp.ProfileView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
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

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun showUserInfo(userInfo: UserInfo) {
        text_profile_userName.text = userInfo.name
        text_profile_email.text = userInfo.email
        text_profile_phone.text = userInfo.phone
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_profile))
        setupToolbarMenu()
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
            texLeftButton = getString(R.string.btn_cancel),
            textRightButton = getString(R.string.btn_yes),
            titleText = getString(R.string.exit_dialog_text),
            buttonRightDialogClickListener = {
                presenter.onLogoutClicked()
            }
        ).show(fragmentManager, "TwoActionAlertDialog")
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
