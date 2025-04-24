package com.gibconsulting.guardianapisample.domain.repository

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.domain.model.ArticleDetails

interface ArticlesRepository {
    suspend fun getLatestArticles(): List<Article>
    suspend fun getArticleDetails(articleUrl: String): ArticleDetails
}