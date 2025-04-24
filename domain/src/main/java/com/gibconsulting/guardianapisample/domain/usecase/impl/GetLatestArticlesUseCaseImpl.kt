package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.domain.repository.ArticlesRepository
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesUseCase
import javax.inject.Inject

internal class GetLatestArticlesUseCaseImpl @Inject constructor(
    private val articlesRepository: ArticlesRepository
) : GetLatestArticlesUseCase {
    override suspend fun invoke(): List<Article> {
        return articlesRepository.getLatestArticles().sortedByDescending { it.published }
    }
}