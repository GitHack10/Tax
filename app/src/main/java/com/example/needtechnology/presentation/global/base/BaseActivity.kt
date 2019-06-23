package com.example.needtechnology.presentation.global.base

import android.os.Bundle
import android.os.PersistableBundle
import com.arellomobile.mvp.MvpAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

abstract class BaseActivity : MvpAppCompatActivity() {

    /** Контейнер в котором будет происходить переключение дочерних фрагментов */
    protected abstract val container: Int

    protected abstract val navigatorHolder: NavigatorHolder

    protected val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(container) as? BaseFragment

    open var navigator: Navigator? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (navigator == null) navigator = SupportAppNavigator(this, container)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}