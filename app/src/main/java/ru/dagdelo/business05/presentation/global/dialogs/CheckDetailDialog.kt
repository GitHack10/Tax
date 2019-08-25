package ru.dagdelo.business05.presentation.global.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.global.models.CheckInfo
import ru.dagdelo.business05.presentation.global.utils.withArgs


class CheckDetailDialog : DialogFragment() {

    private var checkInfo: CheckInfo? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!, R.style.CustomAlertDialog)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_check_detail, null)
        arguments?.apply { checkInfo = getParcelable<CheckInfo>(CHECK) }
        builder.setView(view)
        init(view)
        return builder.create()
    }

    private fun init(view: View) {

        val closeButton: ImageView = view.findViewById(R.id.closeImageView)
        closeButton.setOnClickListener { dialog.cancel() }

        val titleTextView: TextView = view.findViewById(R.id.titleCheckDetail)
        titleTextView.text = "Чек на сумму ${checkInfo?.totalSum}" ?: ""

        val dateTextView: TextView = view.findViewById(R.id.dateCheckDescText)
        dateTextView.text = checkInfo?.dateCheck ?: checkInfo?.dateCheck

        val addressTextView: TextView = view.findViewById(R.id.addressCheckDescText)
        addressTextView.text = checkInfo?.address ?: ""

        val innTextView: TextView = view.findViewById(R.id.innCheckDescText)
        innTextView.text = checkInfo?.inn ?: ""

        val statusTextView: TextView = view.findViewById(R.id.statusCheckDescText)
        when (checkInfo?.status?.type ?: 3) {
            0 -> {
                statusTextView.text = checkInfo?.status?.message
                    ?: resources.getString(R.string.checklist_check_status_illegal)
                statusTextView.setTextColor(view.resources.getColor(R.color.error))
            }
            1 -> {
                statusTextView.text = checkInfo?.status?.message
                    ?: resources.getString(R.string.checklist_check_status_registered)
                statusTextView.setTextColor(view.resources.getColor(R.color.green))
            }
            2 -> {
                statusTextView.text = checkInfo?.status?.message
                    ?: resources.getString(R.string.checklist_check_status_legal)
                statusTextView.setTextColor(view.resources.getColor(R.color.colorAccent))
            }
            3 -> {
                statusTextView.text = checkInfo?.status?.message
                    ?: resources.getString(R.string.checklist_check_status_in_the_treatment)
                statusTextView.setTextColor(view.resources.getColor(R.color.hintTitle))
            }
        }
    }

    companion object {
        fun newInstance(checkInfo: CheckInfo) = CheckDetailDialog().withArgs {
            putParcelable(CHECK, checkInfo)
        }

        const val CHECK = "CHECK"
    }
}