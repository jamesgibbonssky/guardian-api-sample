package com.gibconsulting.guardianapisample.presentation.mainscreen

import android.content.res.Resources
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gibconsulting.guardianapisample.R
import com.gibconsulting.guardianapisample.presentation.common.Udf
import com.gibconsulting.guardianapisample.presentation.common.collectNavigationEffect
import com.gibconsulting.guardianapisample.presentation.common.collectViewEffect
import com.gibconsulting.guardianapisample.presentation.theme.GuardianApiSampleAppTheme
import timber.log.Timber

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigationEffect: (MainScreenNavigationEffect) -> Unit = {},
    viewModel: MainScreenViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.collectNavigationEffect(onNavigationEffect)

    val snackbarHostState = remember { SnackbarHostState() }
    val resources = LocalContext.current.resources
    viewModel.collectViewEffect {
        handleViewEffect(it, snackbarHostState, resources)
    }

    LaunchedEffect(state.articles) {
        viewModel.handleEvent(MainScreenEvent.OnRefresh)
    }

    GuardianApiSampleAppTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = { AppBar(Modifier, stringResource(R.string.app_name)) },
            snackbarHost = {
                SnackbarHost(
                    snackbarHostState,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                )
            }
        ) { innerPadding ->
            MainScreenContent(
                state,
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onItemClicked = { viewModel.handleEvent((MainScreenEvent.OnClicked(it))) },
                onRefresh = { viewModel.handleEvent(MainScreenEvent.OnRefresh) }
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
        is MainScreenViewEffect.ShowError -> {
            val message = resources.getString(it.messageId)
            Timber.d("JGG MainScreen: ShowError: $message")
            snackbarHostState.showSnackbar(message)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
    title: String = "") {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier
    )
}