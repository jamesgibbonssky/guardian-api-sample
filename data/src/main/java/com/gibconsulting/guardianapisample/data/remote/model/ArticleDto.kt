package com.gibconsulting.guardianapisample.data.remote.model

import java.util.Date

internal data class ArticleDto(
    val id: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: Date,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val fields: ArticleFieldsDto?
)

internal data class ArticleFieldsDto(
    val headline: String?,
    val main: String?,
    val body: String?,
    val thumbnail: String?
)