package com.example.needtechnology.presentation.global.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.example.needtechnology.R

fun setWhiteStyleWindow(view: View, activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = view.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility = flags
        activity.window.statusBarColor = Color.WHITE
        activity.window.setBackgroundDrawable(view.resources.getDrawable(R.drawable.background_app))
    }
}

fun setGradientStyleWindow(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val window = activity.window
        val background = activity.resources.getDrawable(R.drawable.background_app)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = activity.resources.getColor(android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }
}