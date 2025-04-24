package com.gibconsulting.guardianapisample.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gibconsulting.guardianapisample.presentation.detailscreen.detailScreenDestination
import com.gibconsulting.guardianapisample.presentation.mainscreen.MainScreenRoute
import com.gibconsulting.guardianapisample.presentation.mainscreen.mainScreenDestination

@Composable
fun GuardianApiSampleApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MainScreenRoute) {
        mainScreenDestination(onNavigationEffect = navController::handleMainScreenNavigation)
        detailScreenDestination(onNavigationEffect = navController::handleDetailScreenNavigation)
    }
}