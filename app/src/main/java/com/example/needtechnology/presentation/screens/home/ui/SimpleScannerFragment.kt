package com.example.needtechnology.presentation.screens.home.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.needtechnology.data.global.local.SharedPreferenceStorage
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class SimpleScannerFragment : Fragment(), ZXingScannerView.ResultHandler {
    private lateinit var mScannerView: ZXingScannerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mScannerView = ZXingScannerView(activity)
        return mScannerView
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun handleResult(rawResult: Result) {
        Toast.makeText(
            context, "Успешно!", Toast.LENGTH_SHORT
        ).show()
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        val handler = Handler()
        handler.postDelayed(
            { mScannerView.resumeCameraPreview(this@SimpleScannerFragment) },
            2000
        )
        val prefs: SharedPreferences =
            context!!.getSharedPreferences(SharedPreferenceStorage.PREF_PROFILE, Context.MODE_PRIVATE)
        prefs.edit().putString("QR_STRING", rawResult.text).apply()
        fragmentManager?.popBackStack()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }
}