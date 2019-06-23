package com.example.needtechnology.presentation.global.navigation

import com.example.needtechnology.presentation.global.navigation.commands.HideCommand
import com.example.needtechnology.presentation.global.navigation.commands.ShowOrOpenCommand
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

class FlowRouter(private val appRouter: Router) : Router() {

    fun showOrOpenScreen(screen: Screen) = executeCommands(ShowOrOpenCommand(screen))
    fun hideScreen(screen: Screen) = executeCommands(HideCommand(screen))

    fun navigateToFlow(screen: Screen) = appRouter.navigateTo(screen)
    fun replaceFlow(screen: Screen) = appRouter.replaceScreen(screen)
    fun exitFlow() = appRouter.exit()
    fun newRootFlow(screen: Screen) = appRouter.newRootScreen(screen)
    fun backToFlow(screen: Screen) = appRouter.backTo(screen)
    fun newChainFlow(vararg screen: Screen) = appRouter.newChain(*screen)
    fun newRooChainFlow(vararg screen: Screen) = appRouter.newRootChain(*screen)
    fun finishChainFlow() = appRouter.finishChain()
}