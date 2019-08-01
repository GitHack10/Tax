package ru.dagdelo.business05.presentation.global.utils

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import ru.dagdelo.business05.domain.ResourceManager
import ru.dagdelo.business05.presentation.global.Screens
import ru.terrakok.cicerone.Router

class ErrorHandler(
    private val router: Router,
    private val networkErrorParser: NetworkErrorParser,
    private val resourceManager: ResourceManager,
    private val prefs: PreferenceStorage
) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit) {
        if (error is HttpException) {
            when (error.code()) {
                401 -> signOut() // Токен истек или не существует
                else -> messageListener(networkErrorParser.parseMessage(error))
            }
        } else {
            messageListener(error.userMessage(resourceManager))
        }
    }

    private fun signOut() {
        prefs.token = ""
        prefs.isLogin = false
        router.replaceScreen(Screens.SignIn())
    }
}