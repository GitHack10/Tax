package com.example.needtechnology.presentation.global.utils

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import com.example.needtechnology.R

fun setGradientStyleWindow(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val window = activity.window
        val background = activity.resources.getDrawable(R.drawable.background_splash)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = activity.resources.getColor(android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }
}