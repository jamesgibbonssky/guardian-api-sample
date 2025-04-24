package com.gibconsulting.guardianapisample.data.remote.mapper

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.data.remote.model.ArticleDtoListResponse
import javax.inject.Inject

internal class ArticleMapper @Inject constructor() {
    fun map(articleDtoListResponse: ArticleDtoListResponse): List<Article> {
        return articleDtoListResponse.response.results.map {
            Article(
                id = it.id,
                thumbnail = it.fields?.thumbnail ?: "",
                sectionId = it.sectionId,
                sectionName = it.sectionName,
                published = it.webPublicationDate,
                title = it.fields?.headline ?: "",
                url = it.apiUrl
            )
        }
    }
}