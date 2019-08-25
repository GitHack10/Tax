package ru.dagdelo.business05.presentation.screens.checklist.ui


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
import ru.dagdelo.business05.R
import ru.dagdelo.business05.domain.global.models.CheckInfo
import ru.dagdelo.business05.presentation.global.base.BaseFragment
import ru.dagdelo.business05.presentation.global.dialogs.TwoActionDialog
import ru.dagdelo.business05.presentation.screens.checklist.mvp.ChecklistPresenter
import ru.dagdelo.business05.presentation.screens.checklist.mvp.ChecklistView
import javax.inject.Inject

class ChecklistFragment : BaseFragment(), ChecklistView, HasSupportFragmentInjector {
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
        checklistRecycler?.let {
            it.apply { (checklistRecycler.adapter as CheckAdapter).isLoading(show) }
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

    override fun showEmptyList(show: Boolean) {
        checklistEmptyLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showContentLayout(show: Boolean) {
        checklistRecycler.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    override fun showCheckList(checkList: List<CheckInfo>) {
        checklistRecycler?.let {
            it.apply { (checklistRecycler.adapter as CheckAdapter).setCheckList(checkList) }
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
                adapter = CheckAdapter(checkList)
            }
        }
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_checklist))
        checklistRecycler.setHasFixedSize(true)

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

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
