package com.gibconsulting.guardianapisample.presentation.common

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class UdfViewModel<VS : Udf.ViewState, VE : Udf.ViewEffect, NE : Udf.NavigationEffect, E : Udf.ViewEvent>(val initialUiState: VS) : ViewModel() {
    private val _uiState: MutableStateFlow<VS> = MutableStateFlow(initialUiState)
    val uiState by lazy { _uiState.asStateFlow() }

    // Using channels in preference to MutableSharedFlow as only a single receiver is needed and so Channels give better performance
    // See: https://medium.com/@math.perroud/understanding-the-differences-between-mutablestateflow-mutablesharedflow-and-channels-in-ffa606764a5b
    private val _viewEffect: Channel<VE> = Channel()
    val viewEffect by lazy { _viewEffect.receiveAsFlow() }

    private val _navigationEffect: Channel<NE> = Channel()
    val navigationEffect by lazy { _navigationEffect.receiveAsFlow() }

    abstract fun handleEvent(event: E)

    protected fun setUiState(reduce: VS.() -> VS) {
        _uiState.update { _uiState.value.reduce() }
    }

    protected fun sendViewEffect(builder: () -> VE) {
        val effectValue = builder()
        viewModelScope.launch {
            _viewEffect.send(effectValue)
        }
    }

    protected fun sendNavigationEffect(builder: () -> NE) {
        val effectValue = builder()
        viewModelScope.launch {
            _navigationEffect.send(effectValue)
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun <NE: Udf.NavigationEffect> UdfViewModel<*, *, NE, *>.collectNavigationEffect(
    onNavigationEffect: (NE) -> Unit = {}
) {
    val nav = navigationEffect
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(nav, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            nav.collect { effect ->
                onNavigationEffect(effect)
            }
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun <VE: Udf.ViewEffect> UdfViewModel<*, VE, *, *>.collectViewEffect(
    onViewEffect: suspend (VE) -> Unit = {}
) {
    val effectFlow = viewEffect
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(effectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            effectFlow.collect { effect ->
                onViewEffect(effect)
            }
        }
    }
}