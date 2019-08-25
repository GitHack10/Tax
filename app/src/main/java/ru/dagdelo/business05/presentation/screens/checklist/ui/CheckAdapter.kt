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
    private val checkList: List<CheckInfo>,
    private val onDetailClickListener: (CheckInfo) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var isLoading = false

    override fun getItemViewType(position: Int): Int {
        return when {
            isLoading -> LOADING_VIEW_TYPE
            else -> DEFAULT_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            LOADING_VIEW_TYPE -> LoadHolder(containerView = parent.inflate(R.layout.item_loading))
            else -> CheckHolder(containerView = parent.inflate(R.layout.item_check))
        }

    override fun getItemCount() = checkList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is CheckHolder -> holder.setData(checkList[position])
            is LoadHolder -> holder.bind()
        }
    }

    fun setCheckList(newList: List<CheckInfo>) {
        val positionStart = this.checkList.size + 1
        (this.checkList as ArrayList).addAll(newList)
        notifyItemRangeInserted(positionStart, newList.size)
    }

    fun isLoading(load: Boolean) {
        this.isLoading = load
        if (load) notifyItemInserted(checkList.size + 1)
        else notifyItemRemoved(checkList.size + 1)
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

    inner class LoadHolder(override val containerView: View) : ViewHolder(containerView),
        LayoutContainer {

        fun bind() {
            checkPaginationProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    companion object {
        const val DEFAULT_VIEW_TYPE = 0
        const val LOADING_VIEW_TYPE = 1
    }
}