package com.gibconsulting.guardianapisample.data.remote.model


internal data class ArticleDtoListResponse(val response: ArticleDtoList)

internal data class ArticleDtoList(val results: List<ArticleDto>)

internal data class ArticleDtoResponse(val response: ArticleDtoContent)

internal data class ArticleDtoContent(val content: ArticleDto)