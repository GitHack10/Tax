package com.example.needtechnology.presentation.global.base

import ru.terrakok.cicerone.NavigatorHolder

/** Абстрактный клас для фрагментов, содержащие дочерние фрагменты */
abstract class FlowFragment : BaseFragment() {

    /** Контейнер в котором будет происходить переключение дочерних фрагментов */
    protected abstract val container: Int

    /** Получение текущего фрагмента */
    protected val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(container) as? BaseFragment

    protected abstract val navigatorHolder: NavigatorHolder
}
