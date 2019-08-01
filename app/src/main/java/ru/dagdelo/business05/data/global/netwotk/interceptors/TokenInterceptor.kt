package ru.dagdelo.business05.data.global.netwotk.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.dagdelo.business05.data.global.local.PreferenceStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    private val userPreferences: PreferenceStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        /*
         * Добавляет header с токеном, только если нет другого Authorization (Basic)
         *
         * Возможна ситуация, когда токен сохранен, но юзер еще не зареган.
         * Например, юзер ввел корректный СМС-код, после чего сохраняется токен
         * и выполняется переход на экран регистрации юзера, но при этом регистрацию не прошел и вышел.
         */
        if (request.headers()["Authorization"] == null) {
            userPreferences.token.let {
                request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${userPreferences.token}")
                    .build()
            }
        }
        return chain.proceed(request)
    }
}