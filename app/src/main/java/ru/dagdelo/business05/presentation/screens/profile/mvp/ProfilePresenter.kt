package ru.dagdelo.business05.presentation.screens.profile.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.domain.global.models.UserInfo
import ru.dagdelo.business05.domain.profile.ProfileInteractor
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
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
        interactor.setIsLogin(isLogin = false)
        appRouter.newRootScreen(Screens.SignIn())
    }

    fun onSaveChangesClicked(username: String) {
        subscription += interactor.editProfile(username)
            .doOnSubscribe { viewState.showSaveProgress(true) }
            .doAfterTerminate { viewState.showSaveProgress(false) }
            .subscribeBy(
                onComplete = {  },
                onError = {
                    viewState.showSaveError("Не удалось сохранить изменения")
                }
            )
    }
}
