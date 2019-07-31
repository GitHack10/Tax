package com.example.needtechnology.presentation.screens.profile.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.needtechnology.domain.global.models.UserInfo
import com.example.needtechnology.domain.profile.ProfileInteractor
import com.example.needtechnology.presentation.global.Screens
import com.example.needtechnology.presentation.global.base.BasePresenter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
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
        getUserInfo()
    }

    fun getUserInfo() {
        viewState.showLoadProgress(true)
        subscription += interactor.getUserInfo()
            .subscribeBy(
                onSuccess = {
                    it.dateProcessing()
                    viewState.showUserInfo(it)
                    viewState.showLoadProgress(false)
                },
                onError = {
                    viewState.showError("Проверьте ваше подключение к интернету")
                    viewState.showLoadProgress(false)
                }
            )
    }

    fun onLogoutClicked() {
        interactor.clearUserData()
        interactor.setIsLogin(false)
        interactor.cleanToken()
        appRouter.newRootScreen(Screens.SignIn())
    }

    fun onSaveChangesClicked(username: String) {
        viewState.showSaveProgress(true)
        subscription += interactor.editProfile(username)
            .subscribeBy(
                onComplete = { viewState.showSaveProgress(false) },
                onError = {
                    viewState.showSaveError("Не удалось сохранить изменения")
                    viewState.showSaveProgress(false)
                }
            )
    }
}
