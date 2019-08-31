package ru.dagdelo.business05.presentation.screens.checklist.ui

import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_check.*
import kotlinx.android.synthetic.main.item_loading.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.global.models.CheckInfo
import ru.dagdelo.business05.presentation.global.utils.inflate

class CheckAdapter(
    private var checkList: List<CheckInfo>,
    private val onDetailClickListener: (CheckInfo) -> Unit
) : RecyclerView.Adapter<CheckAdapter.CheckHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckHolder =
        CheckHolder(containerView = parent.inflate(R.layout.item_check))

    override fun getItemCount() = checkList.size

    override fun onBindViewHolder(holder: CheckHolder, position: Int) {
        holder.setData(checkList[position])
    }

    fun setCheckList(newList: List<CheckInfo>) {
        val positionStart = checkList.size + 1
        (checkList as ArrayList).addAll(newList)
        notifyItemRangeInserted(positionStart, newList.size)
    }

    fun setRefreshedList(newList: List<CheckInfo>) {
        checkList = newList
        notifyDataSetChanged()
    }

    inner class CheckHolder(override val containerView: View) : ViewHolder(containerView),
        LayoutContainer {

        fun setData(check: CheckInfo) {
            checkTitleText.text = check.totalSum
            checkDateText.text = check.dateScan

            when (check.status.type) {
                0 -> {
                    checkStatusDescText.text =
                        containerView.resources.getString(R.string.checklist_check_status_illegal)
                    checkStatusDescText.setTextColor(containerView.resources.getColor(R.color.error))
                }
                1 -> {
                    checkStatusDescText.text =
                        containerView.resources.getString(R.string.checklist_check_status_registered)
                    checkStatusDescText.setTextColor(containerView.resources.getColor(R.color.green))
                }
                2 -> {
                    checkStatusDescText.text =
                        containerView.resources.getString(R.string.checklist_check_status_legal)
                    checkStatusDescText.setTextColor(containerView.resources.getColor(R.color.colorAccent))
                }
                3 -> {
                    checkStatusDescText.text =
                        containerView.resources.getString(R.string.checklist_check_status_in_the_treatment)
                    checkStatusDescText.setTextColor(containerView.resources.getColor(R.color.hintTitle))
                }
            }

            detailImageView.setOnClickListener { onDetailClickListener(check) }
        }
    }
}