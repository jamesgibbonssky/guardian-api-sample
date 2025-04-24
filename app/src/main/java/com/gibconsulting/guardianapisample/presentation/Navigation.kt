package com.gibconsulting.guardianapisample.presentation

import androidx.navigation.NavController
import com.gibconsulting.guardianapisample.presentation.detailscreen.DetailScreenNavigationEffect
import com.gibconsulting.guardianapisample.presentation.detailscreen.navigateToDetailScreen
import com.gibconsulting.guardianapisample.presentation.mainscreen.MainScreenNavigationEffect

// Encapsulate all the navigation here in one place.
// The functions here need visibility of both the origin and destination screens.
// Therefore they need to be defined in a module that depends on both the modules that define those screens.
// This is why this file is in the ui module. Instead of having each navigation function defined in the module that defines the screen.

fun NavController.handleMainScreenNavigation(navigationEffect: MainScreenNavigationEffect) {
    when (navigationEffect) {
        is MainScreenNavigationEffect.NavigateToDetailScreen -> navigateToDetailScreen(navigationEffect.articleId)
    }
}

fun NavController.handleDetailScreenNavigation(navigationEffect: DetailScreenNavigationEffect) {
    when (navigationEffect) {
        DetailScreenNavigationEffect.NavigateBack -> navigateUp()
    }
}
