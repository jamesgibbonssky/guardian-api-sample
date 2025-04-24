package com.gibconsulting.guardianapisample.domain.usecase

import com.gibconsulting.guardianapisample.domain.model.Article

interface GetLatestArticlesGroupedUseCase {
    suspend fun invoke(): Map<String, List<Article>>

    companion object {
        const val FAVOURITE = "Favourites"
        const val WEEK_DATE_PATTERN = "ww-yyyy"

    }
}