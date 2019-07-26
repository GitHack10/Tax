package com.example.needtechnology.presentation.screens.profile.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.domain.global.UserInfo
import com.example.needtechnology.domain.profile.ProfileInteractor
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val appRouter: Router,
    private val interactor: ProfileInteractor,
    private val userInfo: UserInfo
) : BasePresenter<ProfileView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showUserInfo(interactor.getUserInfo())
    }

    fun onLogoutClicked() {
//        interactor.clearUserData()
        interactor.setIsLogin(false)
        appRouter.newRootScreen(Screens.SignIn())
    }

    fun onSaveChangesClicked(username: String, email: String) {
        interactor.saveUserInfo(username, email)
    }
}