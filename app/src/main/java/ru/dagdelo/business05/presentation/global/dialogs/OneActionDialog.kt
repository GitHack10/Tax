package ru.dagdelo.business05.presentation.global.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.TextView
import ru.dagdelo.business05.R

/**
 * @param buttonClickListener яв-ся вашей реализаций кнопки
 * @param textButton яв-ся вашим текстом на кнопке
 * @param titleText яв-ся вашим текстом заголовка
 * @param descText яв-ся вашим текстом описания
 * @param autoClose указывает на то будет ли диалоговое окно закрываться при нажатии на кнопку
 * */
@SuppressLint("ValidFragment")
class OneActionDialog(
    var titleText: String = "Title",
    var descText: String = "Description",
    var textButton: String = "Action",
    var buttonClickListener: (() -> Unit)? = null,
    var autoClose: Boolean = true
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!, R.style.CustomAlertDialog)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_one_action, null)
        builder.setView(view)
        init(view)
        return builder.create()
    }

    private fun init(view: View) {
        isCancelable = autoClose

        val actionButton = view.findViewById<Button>(R.id.buttonOneAction)
        actionButton.text = textButton
        actionButton.setOnClickListener {
            buttonClickListener?.invoke()
            dialog.cancel()
        }

        val titleTextView: TextView = view.findViewById(R.id.titleOneAction)
        titleTextView.text = titleText

        val descTextView: TextView = view.findViewById(R.id.descriptionOneAction)
        descTextView.text = descText
    }
}