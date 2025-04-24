package com.gibconsulting.guardianapisample.presentation.mainscreen

import androidx.annotation.StringRes
import com.gibconsulting.guardianapisample.presentation.common.Udf

data class MainScreenViewState(
    val loading: Boolean = false,
    val articles: List<ArticleUiListItem> = emptyList()
) : Udf.ViewState

sealed class MainScreenEvent : Udf.ViewEvent {
    object OnRefresh: MainScreenEvent()
    class OnClicked(val id: String): MainScreenEvent()
}

sealed class MainScreenNavigationEffect : Udf.NavigationEffect {
    class NavigateToDetailScreen(val articleId: String): MainScreenNavigationEffect()
}

sealed class MainScreenViewEffect : Udf.ViewEffect {
    class ShowError(@StringRes val messageId: Int): MainScreenViewEffect()
}

sealed class ArticleUiListItem {
    data class ArticleItem(val article: ArticleUi): ArticleUiListItem()
    data class HeaderItem(val header: String): ArticleUiListItem()
}

data class ArticleUi(
    val id: String = "",
    val thumbnail: String = "",
    val published: String = "",
    val title: String = ""
)