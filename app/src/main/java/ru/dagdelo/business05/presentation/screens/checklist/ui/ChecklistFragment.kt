package ru.dagdelo.business05.presentation.screens.checklist.ui


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
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

    override fun showError(message: String) {
        TwoActionDialog(
            textLeftButton = getString(R.string.btn_cancel),
            textRightButton = getString(R.string.tryAgain),
            titleText = getString(R.string.isNoNetwork),
            buttonRightDialogClickListener = {
                presenter.getCheckList()
            }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showEmptyList(message: String) {
        listEmptyText.text = message
        listEmptyText.visibility = View.VISIBLE
        checklistRecycler.visibility = View.GONE
    }

    override fun showCheckList(checkList: List<CheckInfo>) {
        listEmptyText.visibility = View.INVISIBLE
        checklistRecycler.apply {
            visibility = View.VISIBLE
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

    private fun init() {
        setupToolbar(getString(R.string.menu_checklist))
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}
