package com.example.needtechnology.presentation.global.utils

import com.example.needtechnology.data.global.local.PreferenceStorage
import com.example.needtechnology.domain.ResourceManager
import com.example.needtechnology.presentation.global.Screens
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
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