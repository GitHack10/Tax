package com.example.needtechnology.presentation.screens.checklist.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.needtechnology.R
import com.example.needtechnology.domain.global.models.CheckInfoEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_check.*

class CheckAdapter(
    private val checkList: List<CheckInfoEntity>
): RecyclerView.Adapter<CheckAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = Holder(
        containerView = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_check,
            viewGroup,
            false
        )
    )

    override fun getItemCount() = checkList.size

    override fun onBindViewHolder(holder: CheckAdapter.Holder, position: Int) {
        holder.setData(checkList[position].document.receipt)
    }

    class Holder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun setData(check: CheckInfoEntity.Document.Receipt) {
            checkTitleText.text = "Чек на ${check.totalSum} \u20BD"
            checkDateText.text = check.dateTime
        }
    }
}