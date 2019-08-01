package ru.dagdelo.business05.presentation.global.navigation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import ru.dagdelo.business05.presentation.global.navigation.commands.HideCommand
import ru.dagdelo.business05.presentation.global.navigation.commands.ShowOrOpenCommand
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command

/** Created by Kamil Abdulatipov on 22.06.2019. */

class SupportHelperNavigator : SupportAppNavigator {

    private var containerId: Int = 0
    private var fragmentManager: FragmentManager? = null
    private var lastHideScreen: Fragment? = null

    constructor(
        fragmentActivity: FragmentActivity?,
        containerId: Int
    ) : super(fragmentActivity, containerId) {
        this.containerId = containerId
    }

    constructor(
        fragmentActivity: FragmentActivity?,
        fragmentManager: FragmentManager,
        containerId: Int
    ) : super(fragmentActivity, fragmentManager, containerId) {
        this.containerId = containerId
        this.fragmentManager = fragmentManager
    }

    /**
     * Переопределнный метод с класса SupportAppNavigator который обрабатывает команды
     *                                                 ShowOrOpenCommand / HideCommand
     * если команда иная, то отображает последний скрытый фрагмент и вызывает стандартную реализацию
     * */
    override fun applyCommand(command: Command) {
        when (command) {
            is ShowOrOpenCommand -> showScreen(command)
            is HideCommand -> hideScreen(command)
            else -> {
                lastHideScreen?.let { fragmentManager?.beginTransaction()?.show(it)?.commitNow() }
                super.applyCommand(command)
            }
        }
    }

    /**
     * отображает фрагмент, если он скрыт. Либо добавляет его в контейнер
     * */
    private fun showScreen(showOrOpenCommand: ShowOrOpenCommand) {
        if (showOrOpenCommand.screen is SupportAppScreen) {
            val fragment = createFragment(showOrOpenCommand.screen)

            if (fragment.isHidden) fragmentManager?.beginTransaction()?.show(fragment)?.commitNow()
            else fragmentManager?.beginTransaction()?.add(containerId, fragment)?.commitNow()
        }
    }

    private fun hideScreen(hideCommand: HideCommand) {
        if (hideCommand.screen is SupportAppScreen) {
            val fragment = createFragment(hideCommand.screen)

            if (!fragment.isHidden) {
                fragmentManager?.beginTransaction()?.hide(fragment)?.commitNow()
                lastHideScreen = fragment
            }
        }
    }
}