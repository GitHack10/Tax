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
 * @param buttonAlertDialogClickListener яв-ся вашей реализаций кнопки
 * @param textButton яв-ся вашим текстом на кнопке
 * @param titleText яв-ся вашим текстом ошибки
 * @param autoCloseLeftButton указывает на то будет ли диалоговое окно закрываться при нажатии на кнопку
 * */
@SuppressLint("ValidFragment")
class TwoActionDialog(
    var titleText: String = "Title",
    var textLeftButton: String = "Action left",
    var textRightButton: String = "Action right",
    var buttonLeftDialogClickListener: (() -> Unit)? = null,
    var buttonRightDialogClickListener: (() -> Unit)? = null,
    var autoClose: Boolean = true
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!, R.style.CustomAlertDialog)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_two_action, null)
        builder.setView(view)
        init(view)
        return builder.create()
    }

    private fun init(view: View) {
        isCancelable = autoClose

        val actionLeftButton: Button = view.findViewById(R.id.Button_TwoActionAlertDialog_actionLeft)
        actionLeftButton.text = textLeftButton
        actionLeftButton.setOnClickListener {
            buttonLeftDialogClickListener?.invoke()
            dialog.cancel()
        }

        val actionRightButton = view.findViewById<Button>(R.id.Button_TwoActionAlertDialog_actionRight)
        actionRightButton.text = textRightButton
        actionRightButton.setOnClickListener {
            buttonRightDialogClickListener?.invoke()
            dialog.cancel()
        }

        val errorTextView: TextView = view.findViewById(R.id.TextView_twoAction_error)
        errorTextView.text = titleText
    }
}
