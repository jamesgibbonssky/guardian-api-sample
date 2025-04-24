package com.gibconsulting.guardianapisample.presentation.detailscreen

import android.content.res.Resources
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gibconsulting.guardianapisample.R
import com.gibconsulting.guardianapisample.presentation.common.Udf
import com.gibconsulting.guardianapisample.presentation.common.collectNavigationEffect
import com.gibconsulting.guardianapisample.presentation.common.collectViewEffect
import com.gibconsulting.guardianapisample.presentation.theme.AppBlue
import com.gibconsulting.guardianapisample.presentation.theme.AppWhite
import com.gibconsulting.guardianapisample.presentation.theme.GuardianApiSampleAppTheme
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    articleId: String,
    modifier: Modifier = Modifier,
    onNavigationEffect: (DetailScreenNavigationEffect) -> Unit = {},
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.collectNavigationEffect(onNavigationEffect)
    val snackbarHostState = remember { SnackbarHostState() }
    val resources = LocalContext.current.resources
    viewModel.collectViewEffect {
        handleViewEffect(it, snackbarHostState, resources)
    }

    LaunchedEffect(articleId) {
        viewModel.handleEvent(DetailScreenEvent.OnDetailScreenDisplayed(articleId))
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    GuardianApiSampleAppTheme {
        val scaffoldModifier =
            modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)

        Scaffold(
            modifier = scaffoldModifier,
            topBar = {
                AppBar(
                    modifier = Modifier,
                    onBackClick = { viewModel.handleEvent(DetailScreenEvent.OnBackClicked) },
                    scrollBehavior = scrollBehavior,
                    showFavoriteFilled = state.isFavorite,
                    onFavouriteClick = { viewModel.handleEvent(DetailScreenEvent.OnFavoriteClicked) }
                )
            },
            snackbarHost = {
                SnackbarHost(
                    snackbarHostState,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                )
            }
        ) { innerPadding ->
                DetailScreenContent(
                    modifier = Modifier.padding(innerPadding),
                    state = state
                )
        }
    }
}

private suspend fun handleViewEffect(
    it: Udf.ViewEffect,
    snackbarHostState: SnackbarHostState,
    resources: Resources
) {
    when (it) {
        is DetailScreenViewEffect.ShowError -> {
            val message = resources.getString(it.messageId)
            Timber.d("JGG Screen2: ShowError: $message")
            snackbarHostState.showSnackbar(message)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    showFavoriteFilled: Boolean = false,
    onFavouriteClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text("") },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button_content_description)
                )
            }
        },
        actions = {
            IconButton(onClick = onFavouriteClick) {
                Icon(
                    imageVector = if (showFavoriteFilled) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.favourite_button_content_description),
                    tint = AppWhite
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                containerColor = AppBlue,
                scrolledContainerColor = AppBlue,
            ),
        scrollBehavior = scrollBehavior
    )
}