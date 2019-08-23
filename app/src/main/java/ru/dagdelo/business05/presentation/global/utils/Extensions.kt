package ru.dagdelo.business05.presentation.global.utils

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import ru.dagdelo.business05.R

fun setWhiteStyleWindow(view: View, activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = view.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility = flags
        activity.window.setBackgroundDrawable(view.resources.getDrawable(R.drawable.background_app))
    }
}

fun setGradientStyleWindow(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val window = activity.window
        val background = activity.resources.getDrawable(R.drawable.background_app)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setBackgroundDrawable(background)
    }
}

fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(
    layoutId,
    this,
    false
)!!

inline fun <T : Fragment> T.withArgs(
    argsBuilder: Bundle.() -> Unit
): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }

fun String.regexPhone() = replace("+", "")
    .replace("(", "")
    .replace(")", "")
    .replace("-", "")
    .replace(" ", "")