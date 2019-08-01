package ru.dagdelo.business05.presentation.global

import ru.dagdelo.business05.presentation.screens.auth.signin.ui.SignInFragment
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
        override fun getFragment() = SignInFragment()
    }

    class SignUp : SupportAppScreen() {
        override fun getFragment() = SignUpFragment()
    }

    class MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    class Home : SupportAppScreen() {
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