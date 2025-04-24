package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.model.ArticleDetails
import com.gibconsulting.guardianapisample.domain.repository.ArticlesRepository
import com.gibconsulting.guardianapisample.domain.usecase.GetArticleDetailsUseCase
import javax.inject.Inject

internal class GetArticleDetailsUseCaseImpl @Inject constructor(
    private val articlesRepository: ArticlesRepository
) : GetArticleDetailsUseCase {
    override suspend fun invoke(id: String): ArticleDetails {
        return articlesRepository.getArticleDetails(id)
    }
}