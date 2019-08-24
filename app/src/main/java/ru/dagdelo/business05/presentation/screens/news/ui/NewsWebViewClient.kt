package ru.dagdelo.business05.presentation.screens.news.ui

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient

class NewsWebViewClient(
    val url: String,
    val progressListener: ((Boolean) -> Unit)? = null
) : WebViewClient() {

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        progressListener?.invoke(true)
    }

    override fun onPageFinished(view: WebView, url: String) {
        progressListener?.invoke(false)
    }
}