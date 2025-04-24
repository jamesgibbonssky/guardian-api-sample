package com.gibconsulting.guardianapisample.presentation.mainscreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object MainScreenRoute

fun NavGraphBuilder.mainScreenDestination(
    onNavigationEffect: (MainScreenNavigationEffect) -> Unit = {}
) {
    composable<MainScreenRoute> {
        MainScreen(onNavigationEffect = onNavigationEffect)
    }
}