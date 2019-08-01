package ru.dagdelo.business05.presentation.global.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.support.v4.findOptional
import ru.dagdelo.business05.R

abstract class BaseFragment : MvpAppCompatFragment() {
    protected abstract val layoutRes: Int
    protected val subscriptions = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    protected fun setupToolbar(title: String, showNavIcon: Boolean = false) {
        val toolbar: Toolbar? = findOptional(R.id.toolbar)
        toolbar?.run {
            if (showNavIcon) {
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener { onBackPressed() }
                navIconPlaceholder.visibility = View.GONE
            }
            titleText.text = title
        }
    }

    abstract fun onBackPressed()
}