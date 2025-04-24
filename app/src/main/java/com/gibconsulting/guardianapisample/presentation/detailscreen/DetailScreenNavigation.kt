package com.gibconsulting.guardianapisample.presentation.detailscreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class DetailScreenRoute(
    val articleId: String
)

fun NavGraphBuilder.detailScreenDestination(
    onNavigationEffect: (DetailScreenNavigationEffect) -> Unit = {}
) {
    composable<DetailScreenRoute> { backStackEntry ->
        val route: DetailScreenRoute = backStackEntry.toRoute()
        DetailScreen(
            articleId = route.articleId,
            onNavigationEffect = onNavigationEffect)
    }
}

fun NavController.navigateToDetailScreen(articleId: String) {
    navigate(DetailScreenRoute(articleId))
}