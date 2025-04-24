package com.gibconsulting.guardianapisample.data.remote.mapper

import com.gibconsulting.guardianapisample.data.remote.model.ArticleDto
import com.gibconsulting.guardianapisample.domain.model.ArticleDetails
import javax.inject.Inject

internal class ArticleDetailsMapper @Inject constructor() {
    fun map(articleDto: ArticleDto): ArticleDetails {
        return with (articleDto) {
            ArticleDetails(
                id = id,
                headline = fields?.headline ?: "",
                body = fields?.body ?: "",
                imageUrl = fields?.thumbnail ?: ""
            )
        }
    }
}