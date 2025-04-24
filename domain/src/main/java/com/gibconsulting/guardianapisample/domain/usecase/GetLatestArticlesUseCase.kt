package com.gibconsulting.guardianapisample.domain.usecase

import com.gibconsulting.guardianapisample.domain.model.Article

interface GetLatestArticlesUseCase {
    suspend fun invoke(): List<Article>
}