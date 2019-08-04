package ru.dagdelo.business05.presentation.screens.home.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import ru.dagdelo.business05.data.global.local.SharedPreferenceStorage

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
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        val handler = Handler()
        handler.postDelayed(
            { mScannerView.resumeCameraPreview(this@SimpleScannerFragment) },
            2000
        )
        if (rawResult.text.contains("&") && rawResult.text.contains("fp=")
            && rawResult.text.contains("fn=") && rawResult.text.contains("i=")) {
            val prefs: SharedPreferences =
                context!!.getSharedPreferences(SharedPreferenceStorage.PREF_PROFILE, Context.MODE_PRIVATE)
            prefs.edit().putString("PREF_QR_STRING", rawResult.text).apply()
        } else Toast.makeText(
            context, "Не удалось просканировать, убедитесь что это QR-code чека!", Toast.LENGTH_SHORT
        ).show()
        fragmentManager?.popBackStack()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }
}