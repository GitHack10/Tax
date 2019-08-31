package ru.dagdelo.business05.presentation.global.dialogs

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import kotlinx.android.synthetic.main.dialog_filter_checklist.view.*
import ru.dagdelo.business05.R

class CheckFilterDialog @SuppressLint("ValidFragment") constructor(
    var filterListener: ((Int?) -> Unit)? = null
) : BottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        val dialog = BottomSheetDialog(context!!, R.style.CustomBottomSheetDialog)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_filter_checklist, null)
        dialog.setContentView(view)
        init(view)
        return dialog
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.dialogAllChecksText -> {
                filterListener?.invoke(null)
                dialog.cancel()
            }
            R.id.dialogIllegalChecksText -> {
                filterListener?.invoke(0)
                dialog.cancel()
            }
            R.id.dialogRegisteredChecksText -> {
                filterListener?.invoke(1)
                dialog.cancel()
            }
            R.id.dialogLegalChecksText -> {
                filterListener?.invoke(2)
                dialog.cancel()
            }
            R.id.dialogTreatmentChecksText -> {
                filterListener?.invoke(3)
                dialog.cancel()
            }
        }
    }
    private fun init(view: View) {
        view.dialogAllChecksText.setOnClickListener(this)
        view.dialogRegisteredChecksText.setOnClickListener(this)
        view.dialogLegalChecksText.setOnClickListener(this)
        view.dialogIllegalChecksText.setOnClickListener(this)
        view.dialogTreatmentChecksText.setOnClickListener(this)
    }
}