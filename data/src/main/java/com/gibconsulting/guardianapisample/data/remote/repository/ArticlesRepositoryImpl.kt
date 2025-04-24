package com.gibconsulting.guardianapisample.data.remote.repository

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.domain.model.ArticleDetails
import com.gibconsulting.guardianapisample.data.remote.client.GuardianClient
import com.gibconsulting.guardianapisample.data.remote.di.DispatcherIO
import com.gibconsulting.guardianapisample.data.remote.mapper.ArticleDetailsMapper
import com.gibconsulting.guardianapisample.data.remote.mapper.ArticleMapper
import com.gibconsulting.guardianapisample.domain.repository.ArticlesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ArticlesRepositoryImpl @Inject constructor(
    private val guardianClient: GuardianClient,
    private val articleMapper: ArticleMapper,
    private val articleDetailsMapper: ArticleDetailsMapper,
    @DispatcherIO private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ArticlesRepository {
    override suspend fun getLatestArticles(): List<Article> {
        return withContext(dispatcher) {
            guardianClient.searchArticles("politics").let {
                articleMapper.map(it)
            }
        }
    }

    override suspend fun getArticleDetails(articleUrl: String): ArticleDetails {
        return withContext(dispatcher) {
            guardianClient.getArticle(articleUrl, "main,body,headline,thumbnail").let {
                articleDetailsMapper.map(it.response.content)
            }
        }
    }
}