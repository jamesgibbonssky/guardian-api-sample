package com.gibconsulting.guardianapisample.presentation.detailscreen

import androidx.annotation.StringRes
import com.gibconsulting.guardianapisample.presentation.common.Udf

data class DetailScreenViewState(
    val loading: Boolean = false,
    val article: ArticleDetailsUi? = null,
    val isFavorite: Boolean = false
) : Udf.ViewState

sealed class DetailScreenEvent : Udf.ViewEvent {
    class OnDetailScreenDisplayed(val articleId: String): DetailScreenEvent()
    object OnBackClicked: DetailScreenEvent()
    object OnFavoriteClicked: DetailScreenEvent()
}

sealed class DetailScreenNavigationEffect : Udf.NavigationEffect {
    object NavigateBack: DetailScreenNavigationEffect()
}

sealed class DetailScreenViewEffect : Udf.ViewEffect {
    class ShowError(@StringRes val messageId: Int): DetailScreenViewEffect()
}

data class ArticleDetailsUi(
    val id: String,
    val headline: String,
    val body: String,
    val imageUrl: String
)