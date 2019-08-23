package ru.dagdelo.business05.presentation.global

import ru.dagdelo.business05.presentation.screens.auth.entercode.ui.EnterCodeFragment
import ru.dagdelo.business05.presentation.screens.auth.enterphone.ui.EnterPhoneFragment
import ru.dagdelo.business05.presentation.screens.auth.signup.ui.SignUpFragment
import ru.dagdelo.business05.presentation.screens.checklist.ui.ChecklistFragment
import ru.dagdelo.business05.presentation.screens.home.ui.HomeFragment
import ru.dagdelo.business05.presentation.screens.home.ui.SimpleScannerFragment
import ru.dagdelo.business05.presentation.screens.mainflow.ui.MainFlowFragment
import ru.dagdelo.business05.presentation.screens.news.ui.NewsFragment
import ru.dagdelo.business05.presentation.screens.profile.ui.ProfileFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

/** Created by Kamil Abdulatipov on 18.04.2019. */

object Screens {

    class SignIn : SupportAppScreen() {
        override fun getFragment() = EnterPhoneFragment()
    }

    data class SignUp(private val maskedPhone: String) : SupportAppScreen() {
        override fun getFragment() = SignUpFragment.newInstance(maskedPhone)
    }

    object EnterPhone : SupportAppScreen() {
        override fun getFragment() = EnterPhoneFragment()
    }

    data class EnterCode(
        val maskedPhone: String,
        val phone: String
    ) : SupportAppScreen() {
        override fun getFragment() = EnterCodeFragment.newInstance(maskedPhone, phone)
    }

    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object Home : SupportAppScreen() {
        override fun getFragment() = HomeFragment()
    }

    class Checklist : SupportAppScreen() {
        override fun getFragment() = ChecklistFragment()
    }

    class News : SupportAppScreen() {
        override fun getFragment() = NewsFragment()
    }

    class Profile : SupportAppScreen() {
        override fun getFragment() = ProfileFragment()
    }

    class QrScreen : SupportAppScreen() {
        override fun getFragment() = SimpleScannerFragment()
    }
}