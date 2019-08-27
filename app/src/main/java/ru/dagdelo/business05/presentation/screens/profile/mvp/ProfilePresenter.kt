package ru.dagdelo.business05.presentation.screens.profile.mvp

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.dagdelo.business05.R
import ru.dagdelo.business05.data.global.netwotk.utils.NetworkChecking
import ru.dagdelo.business05.domain.global.models.UserInfo
import ru.dagdelo.business05.domain.profile.ProfileInteractor
import ru.dagdelo.business05.presentation.global.AndroidResourceManager
import ru.dagdelo.business05.presentation.global.Screens
import ru.dagdelo.business05.presentation.global.base.BasePresenter
import ru.dagdelo.business05.presentation.global.utils.ErrorHandler
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val appRouter: Router,
    private val interactor: ProfileInteractor,
    private var userInfo: UserInfo,
    private val errorHandler: ErrorHandler,
    private val resourceManager: AndroidResourceManager,
    private val networkChecking: NetworkChecking
) : BasePresenter<ProfileView>(appRouter) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getUserInfoWithChecking()
    }

    private fun getUserInfoWithChecking() {
        userInfo.user?.let {
            viewState.showUserInfo(userInfo.user!!)
            viewState.showLoadProgress(false)
            viewState.showContentLayout(true)
            viewState.showNoNetworkLayout(false)
        } ?: run {
            if (networkChecking.hasConnection()) getUserInfo()
            else {
                viewState.showLoadProgress(false)
                viewState.showNoNetworkLayout(true)
            }
        }
    }

    private fun getUserInfo() {
        viewState.showNoNetworkLayout(false)
        subscription += interactor.getUserInfo()
            .doOnSubscribe { viewState.showLoadProgress(true) }
            .doAfterTerminate { viewState.showLoadProgress(false) }
            .subscribeBy(
                onSuccess = {
                    it.dateProcessing()
                    userInfo.user = it
                    Timber.d("UserInfo: ${userInfo.user?.phone}")
                    viewState.showUserInfo(it)
                    viewState.showContentLayout(true)
                },
                onError = {
                    userInfo.user?.let {
                        viewState.showUserInfo(userInfo.user!!)
                    } ?: run {
                        if (networkChecking.hasConnection()) {
                            errorHandler.proceed(it) { error ->
                                viewState.showError(
                                    if (!error.errors.isNullOrEmpty()) error.errors.first().message
                                    else error.message
                                )
                            }
                        } else viewState.showError(resourceManager.getString(R.string.checkYourInternetConnection))
                    }
                }
            )
    }

    fun onSaveChangesClicked(username: String, email: String) {
        subscription += interactor.editProfile(username, email)
            .doOnSubscribe { viewState.showSaveProgress(true) }
            .doAfterTerminate { viewState.showSaveProgress(false) }
            .subscribeBy(
                onComplete = {
                    userInfo.user?.let {
                        userInfo.user?.fullName = username
                        userInfo.user?.email = email
                    }
                },
                onError = {
                    if (networkChecking.hasConnection()) {
                        errorHandler.proceed(it) { error ->
                            viewState.showError(
                                if (!error.errors.isNullOrEmpty()) error.errors.first().message
                                else error.message
                            )
                        }
                    } else viewState.showError(resourceManager.getString(R.string.checkYourInternetConnection))
                }
            )
    }

    fun onLogoutClicked() {
        interactor.setIsLogin(isLogin = false)
        userInfo.user = null
        appRouter.newRootScreen(Screens.SignIn())
    }

    fun onTryAgainClicked() = getUserInfoWithChecking()
}
