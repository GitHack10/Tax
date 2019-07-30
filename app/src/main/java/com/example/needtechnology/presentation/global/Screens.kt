package com.example.needtechnology.presentation.global

import com.example.needtechnology.presentation.screens.auth.signin.ui.SignInFragment
import com.example.needtechnology.presentation.screens.auth.signup.ui.SignUpFragment
import com.example.needtechnology.presentation.screens.checklist.ui.ChecklistFragment
import com.example.needtechnology.presentation.screens.home.ui.HomeFragment
import com.example.needtechnology.presentation.screens.home.ui.SimpleScannerFragment
import com.example.needtechnology.presentation.screens.mainflow.ui.MainFlowFragment
import com.example.needtechnology.presentation.screens.news.ui.NewsFragment
import com.example.needtechnology.presentation.screens.profile.ui.ProfileFragment
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