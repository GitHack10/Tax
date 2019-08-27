package ru.dagdelo.business05.presentation.screens.news.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.webkit.WebViewClient
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.no_network.*
import ru.dagdelo.business05.R
import ru.dagdelo.business05.presentation.global.base.BaseFragment
import ru.dagdelo.business05.presentation.global.dialogs.TwoActionDialog
import ru.dagdelo.business05.presentation.screens.news.mvp.NewsPresenter
import ru.dagdelo.business05.presentation.screens.news.mvp.NewsView
import javax.inject.Inject

class NewsFragment : BaseFragment(), NewsView, HasSupportFragmentInjector {
    override val layoutRes = R.layout.fragment_news

    override fun supportFragmentInjector() = fragmentInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    @InjectPresenter
    lateinit var presenter: NewsPresenter

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

    override fun onResume() {
        super.onResume()
        newsWebView.onResume()
    }

    override fun showError(message: String) {
        TwoActionDialog(
            titleText = message,
            textRightButton = getString(R.string.try_again),
            textLeftButton = getString(R.string.cancel),
            buttonRightDialogClickListener = { presenter.retryLoad() }
        ).show(fragmentManager, "TwoActionDialog.javaClass.simpleName")
    }

    override fun showNoNetworkLayout(show: Boolean) {
        if (show) {
            noNetworkLayout.visibility = View.VISIBLE
            newsProgress.visibility = View.INVISIBLE
            newsWebView.visibility = View.GONE
        } else {
            noNetworkLayout.visibility = View.GONE
            newsWebView.visibility = View.VISIBLE
        }
    }

    override fun showNews() {
        newsWebView.webViewClient = NewsWebViewClient(NEWS_URL) { show ->
            newsProgress?.let { it.visibility = if (show) View.VISIBLE else View.INVISIBLE }
        }
        newsWebView.loadUrl(NEWS_URL)
    }

    override fun onPause() {
        newsWebView.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        newsWebView.destroy()
        super.onDestroyView()
    }

    private fun init() {
        setupToolbar(getString(R.string.menu_news))
        networkCheckButton.setOnClickListener {
            newsProgress.visibility = View.VISIBLE
            presenter.retryLoad()
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        private const val NEWS_URL = "http://dagdelo.ru/news"
    }
}