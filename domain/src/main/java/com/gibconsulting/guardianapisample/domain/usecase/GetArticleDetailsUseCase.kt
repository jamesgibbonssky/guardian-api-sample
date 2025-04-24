package com.gibconsulting.guardianapisample.domain.usecase

import com.gibconsulting.guardianapisample.domain.model.ArticleDetails

interface GetArticleDetailsUseCase {
    suspend fun invoke(id: String): ArticleDetails
}