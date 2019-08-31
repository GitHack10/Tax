package ru.dagdelo.business05.presentation.screens.checklist.ui


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.empty_checklist.*
import kotlinx.android.synthetic.main.fragment_checklist.*
import kotlinx.android.synthetic.main.no_network.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.support.v4.dip
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.global.models.CheckInfo
import ru.dagdelo.business05.presentation.global.base.BaseFragment
import ru.dagdelo.business05.presentation.global.dialogs.CheckDetailDialog
import ru.dagdelo.business05.presentation.global.dialogs.CheckFilterDialog
import ru.dagdelo.business05.presentation.global.dialogs.TwoActionDialog
import ru.dagdelo.business05.presentation.screens.checklist.mvp.ChecklistPresenter
import ru.dagdelo.business05.presentation.screens.checklist.mvp.ChecklistView
import javax.inject.Inject

class ChecklistFragment : BaseFragment(), ChecklistView, HasSupportFragmentInjector, SwipeRefreshLayout.OnRefreshListener {
    override val layoutRes = R.layout.fragment_checklist

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: ChecklistPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    // отслеживание пагинации
    private var mVisibleItemCount = 0
    private var mTotalItemCount = 0
    private var mFirstVisibleItem = 0

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun showProgress(show: Boolean) {
        checkListProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showPaginationProgress(show: Boolean) {
        if (show) {
            checklistRecycler.setPadding(0, 0, 0, dip(77))
            checkListPaginationProgress.visibility = View.VISIBLE

        } else {
            checkListPaginationProgress.visibility = View.INVISIBLE
            checklistRecycler.setPadding(0, 0, 0, 0)
        }
    }

    override fun showError(message: String) {
        TwoActionDialog(
            titleText = message,
            textLeftButton = getString(R.string.btn_cancel),
            textRightButton = getString(R.string.try_again),
            buttonRightDialogClickListener = {
                presenter.getCheckList()
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun hideRefreshingProgress() {
        checklistSwipeRefreshLayout.isRefreshing = false
    }

    override fun showNoNetworkLayout(show: Boolean) {
        noNetworkLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showEmptyList(show: Boolean) {
        checklistEmptyLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showContentLayout(show: Boolean) {
        checklistRecycler.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showCheckList(checkList: List<CheckInfo>) {
        checklistRecycler.adapter?.let {
            (it as CheckAdapter).setCheckList(checkList)
        } ?: run {
            checklistRecycler.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                val dividerItemDecoration = DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
                dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_item))
                addItemDecoration(dividerItemDecoration)
                adapter = CheckAdapter(checkList) { check -> onDetailClicked(check) }
            }
        }
    }

    override fun showRefreshedList(newList: List<CheckInfo>) {
        checklistRecycler.adapter?.let {
            (it as CheckAdapter).setRefreshedList(newList)
        }
    }

    override fun showFilteredList(filteredList: List<CheckInfo>) {
        checklistRecycler.adapter?.let {
            (it as CheckAdapter).setFilteredList(filteredList)
        }
    }

    override fun onRefresh() {
        // Fetching data from server
        presenter.onRefreshCheckList()
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_checklist))
        setupToolbarMenu()
        checklistRecycler.setHasFixedSize(true)
        checklistSwipeRefreshLayout.setOnRefreshListener(this)
        checklistSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorAccent))
        networkCheckButton.setOnClickListener { presenter.onTryAgainClicked() }

        // отслеживание скрола
        checklistRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    mVisibleItemCount = recyclerView.childCount
                    mTotalItemCount = recyclerView.layoutManager?.itemCount!!
                    mFirstVisibleItem =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (!presenter.isLoading
                        && !presenter.paginationEnd
                        && mVisibleItemCount + mFirstVisibleItem >= mTotalItemCount
                    ) {
                        presenter.getCheckList()
                    }
                }
            }
        })
    }

    private fun onDetailClicked(check: CheckInfo) {
        CheckDetailDialog.newInstance(check)
            .show(fragmentManager, "CheckDetailDialog.javaClass.simpleName")
    }

    private fun onFilterIconClicked() {
        CheckFilterDialog { presenter.onFilterSelected(it) }
            .show(fragmentManager, "CheckFilterDialog.javaClass.simpleName")
    }

    private fun setupToolbarMenu() {
        menuIconPlaceholder.visibility = View.GONE
        toolbar.run {
            // Не добавляет меню, если уже имеется
            if (menu.size() <= 0) {
                inflateMenu(R.menu.filtered)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_filter -> {
                            onFilterIconClicked()
                            true
                        }

                        else -> false
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
