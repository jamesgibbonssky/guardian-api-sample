package com.gibconsulting.guardianapisample.presentation.detailscreen.mapper

import com.gibconsulting.guardianapisample.domain.model.ArticleDetails
import com.gibconsulting.guardianapisample.presentation.detailscreen.ArticleDetailsUi
import javax.inject.Inject

class ArticleDetailsToArticleDetailsUiMapper @Inject constructor() {
    fun mapToUi(articleDetails: ArticleDetails): ArticleDetailsUi {
        return with (articleDetails) {
            ArticleDetailsUi(
                id = id,
                headline = headline,
                body = body,
                imageUrl = imageUrl
            )
        }
    }
}