package com.gibconsulting.guardianapisample.presentation.detailscreen

import androidx.lifecycle.viewModelScope
import com.gibconsulting.guardianapisample.R
import com.gibconsulting.guardianapisample.presentation.common.Udf
import com.gibconsulting.guardianapisample.presentation.common.UdfViewModel
import com.gibconsulting.guardianapisample.presentation.detailscreen.mapper.ArticleDetailsToArticleDetailsUiMapper
import com.gibconsulting.guardianapisample.domain.usecase.AddToFavoriteArticlesUseCase
import com.gibconsulting.guardianapisample.domain.usecase.GetArticleDetailsUseCase
import com.gibconsulting.guardianapisample.domain.usecase.IsFavoriteArticleUseCase
import com.gibconsulting.guardianapisample.domain.usecase.RemoveFromFavoriteArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getArticleDetailsUseCase: GetArticleDetailsUseCase,
    private val addToFavoriteArticlesUseCase: AddToFavoriteArticlesUseCase,
    private val removeFromFavoriteArticlesUseCase: RemoveFromFavoriteArticlesUseCase,
    private val isFavoriteArticleUseCase: IsFavoriteArticleUseCase,
    private val articleDetailsToArticleDetailsUiMapper: ArticleDetailsToArticleDetailsUiMapper
) : UdfViewModel<DetailScreenViewState, Udf.ViewEffect, DetailScreenNavigationEffect, DetailScreenEvent>(initialUiState = DetailScreenViewState(loading = true)) {

    override fun handleEvent(event: DetailScreenEvent) {
        println("JGG" + "handleEvent() called with: event = $event")
        when (event) {
            is DetailScreenEvent.OnDetailScreenDisplayed -> {
                viewModelScope.launch {
                    try {
                        val article = articleDetailsToArticleDetailsUiMapper.mapToUi(
                            getArticleDetailsUseCase.invoke(event.articleId)
                        )
                        setUiState {
                            copy(article = article, loading = false)
                        }
                        launch {
                            isFavoriteArticleUseCase.invoke(event.articleId).collect {
                                setUiState { copy(isFavorite = it) }
                            }
                        }
                    } catch (e: Exception) {
                        Timber.e(e, "JGG Error fetching article")
                        sendViewEffect {
                            DetailScreenViewEffect.ShowError(R.string.fetch_article_error)
                        }
                        setUiState { copy(loading = false) }
                    }
                }
            }

            DetailScreenEvent.OnBackClicked -> sendNavigationEffect {
                DetailScreenNavigationEffect.NavigateBack
            }

            DetailScreenEvent.OnFavoriteClicked -> {
                Timber.d("JGG handleEvent: OnFavoriteClicked")
                viewModelScope.launch {
                    uiState.value.article?.let { article ->
                        if (isFavoriteArticleUseCase.invoke(article.id).first()) {
                            removeFromFavoriteArticlesUseCase.invoke(article.id)
                        } else {
                            addToFavoriteArticlesUseCase.invoke(article.id)
                        }
                    }
                }
            }
        }
    }
}