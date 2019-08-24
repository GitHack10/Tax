package ru.dagdelo.business05.presentation.screens.checklist.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_check.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.global.models.CheckInfo
import ru.dagdelo.business05.presentation.global.utils.inflate

class CheckAdapter(
    private val checkList: List<CheckInfo>
): RecyclerView.Adapter<CheckAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        containerView = parent.inflate(R.layout.item_check)
    )

    override fun getItemCount() = checkList.size

    override fun onBindViewHolder(holder: CheckAdapter.Holder, position: Int) {
        holder.setData(checkList[position])
    }

    class Holder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun setData(check: CheckInfo) {
            checkTitleText.text = "${check.totalSum} \u20BD"
            checkDateText.text = check.date

            when (check.status) {
                0 -> {
                    checkStatusDescText.text = containerView.resources.getString(R.string.checklist_check_status_illegal)
                    checkStatusDescText.setTextColor(containerView.resources.getColor(R.color.error))
                }
                1 -> {
                    checkStatusDescText.text = containerView.resources.getString(R.string.checklist_check_status_registered)
                    checkStatusDescText.setTextColor(containerView.resources.getColor(R.color.green))
                }
                2 -> {
                    checkStatusDescText.text = containerView.resources.getString(R.string.checklist_check_status_legal)
                    checkStatusDescText.setTextColor(containerView.resources.getColor(R.color.colorAccent))
                }
                3 -> {
                    checkStatusDescText.text = containerView.resources.getString(R.string.checklist_check_status_in_the_treatment)
                    checkStatusDescText.setTextColor(containerView.resources.getColor(R.color.hintTitle))
                }
            }
        }
    }
}