package com.gibconsulting.guardianapisample.presentation.mainscreen

import androidx.lifecycle.viewModelScope
import com.gibconsulting.guardianapisample.R
import com.gibconsulting.guardianapisample.presentation.common.Udf
import com.gibconsulting.guardianapisample.presentation.common.UdfViewModel
import com.gibconsulting.guardianapisample.presentation.mainscreen.mapper.GroupedArticleMapToArticleUiListMapper
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getLatestArticlesUseCase: GetLatestArticlesGroupedUseCase,
    private val groupedArticleMapToArticleUiListMapper: GroupedArticleMapToArticleUiListMapper
) : UdfViewModel<MainScreenViewState, Udf.ViewEffect, MainScreenNavigationEffect, MainScreenEvent>(initialUiState = initialState()) {

    init {
        refresh()
    }

    override fun handleEvent(event: MainScreenEvent) {
        Timber.d("JGG" + "handleEvent() called with: event = $event")
        when (event) {
            is MainScreenEvent.OnClicked -> {
                sendNavigationEffect {
                    MainScreenNavigationEffect.NavigateToDetailScreen(event.id)
                }
            }

            MainScreenEvent.OnRefresh -> {
                setUiState {
                    copy(
                        loading = true,
                    )
                }
                refresh()
            }
        }

    }

    private fun refresh() {
        viewModelScope.launch {
            try {
                val articles = getLatestArticlesUseCase.invoke()
                Timber.d("JGG articles: $articles")
                setUiState {
                    copy(
                        articles = groupedArticleMapToArticleUiListMapper.mapToUi(articles),
                        loading = false
                    )
                }
            } catch (e: Exception) {
                Timber.e(e, "JGG Error fetching articles")
                sendViewEffect {
                    MainScreenViewEffect.ShowError(R.string.fetch_articles_error)
                }
                delay(1000) // This is needed to workaround an issue with the PullToRefreshBox not hiding the loading indicator if the state
                // changes from refreshing true to false too quickly
                setUiState { copy(loading = false) }
            }
        }
    }

    companion object {
        private fun initialState() = MainScreenViewState(
            loading = true
        )
    }
}