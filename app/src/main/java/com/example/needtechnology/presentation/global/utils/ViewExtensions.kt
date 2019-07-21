package com.example.needtechnology.presentation.global.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.needtechnology.R

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

/**
 * Устанавливает доступность [View].
 * Цвета для закрашивания берутся из ресурсов приложения.
 *
 * **ВАЖНО:** Данный метод необходимо вызвать ПОСЛЕ вызова метода `setOnClickListener`,
 * иначе (если вызвать до) не будет работать, потому что `setOnClickListener` изменяет значение `isClickable`.
 */
fun View.accessible(access: Boolean) {
    val background = if (access) R.drawable.button_enabled else R.drawable.button_disabled
    setBackgroundResource(background)
    isClickable = access
}