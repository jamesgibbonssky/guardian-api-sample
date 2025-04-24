package com.gibconsulting.guardianapisample.presentation.mainscreen.previewmocks

import com.gibconsulting.guardianapisample.presentation.mainscreen.ArticleUi
import com.gibconsulting.guardianapisample.presentation.mainscreen.ArticleUiListItem

val articleListPreviewMock = listOf(
    ArticleUiListItem.HeaderItem("Favourites"),
    ArticleUiListItem.ArticleItem(
        ArticleUi(
            id = "1",
            published = "18-04-2005",
            title = "Title 1"
        )
    ),
    ArticleUiListItem.ArticleItem(
        ArticleUi(
            id = "2",
            published = "17-04-2005",
            title = "Title 2"
        )
    ),
    ArticleUiListItem.ArticleItem(
        ArticleUi(
            id = "3",
            published = "16-04-2005",
            title = "Title 3"
        )
    ),
    ArticleUiListItem.HeaderItem("This week"),
    ArticleUiListItem.ArticleItem(
        ArticleUi(
            id = "4",
            published = "18-04-2005",
            title = "Title 4"
        )
    ),
    ArticleUiListItem.ArticleItem(
        ArticleUi(
            id = "5",
            published = "17-04-2005",
            title = "Title 5"
        )
    ),
    ArticleUiListItem.ArticleItem(
        ArticleUi(
            id = "6",
            published = "16-04-2005",
            title = "Title 6"
        )
    )
)