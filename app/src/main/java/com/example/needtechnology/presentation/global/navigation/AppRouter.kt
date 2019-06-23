package com.example.needtechnology.presentation.global.navigation

import com.example.needtechnology.presentation.global.navigation.commands.HideCommand
import com.example.needtechnology.presentation.global.navigation.commands.ShowOrOpenCommand
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

/** Created by Kamil Abdulatipov on 22.06.2019. */

class AppRouter : Router() {

    fun showOrOpenScreen(screen: Screen) = executeCommands(ShowOrOpenCommand(screen))
    fun hideScreen(screen: Screen) = executeCommands(HideCommand(screen))
}