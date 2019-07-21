package com.example.needtechnology.presentation.global.utils

import android.app.Activity
import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Fragment.hideKeyboard() = activity?.hideKeyboard()

fun Activity.hideKeyboard() = hideKeyboard(if (currentFocus == null) View(this) else currentFocus)

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)