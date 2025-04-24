package com.gibconsulting.guardianapisample.data.remote.client

import com.gibconsulting.guardianapisample.data.remote.model.ArticleDtoListResponse
import com.gibconsulting.guardianapisample.data.remote.model.ArticleDtoResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

internal interface GuardianClient {
    @GET("search?show-fields=headline,thumbnail&page-size=50")
    suspend fun searchArticles(@Query("q") searchTerm: String): ArticleDtoListResponse

    @GET
    suspend fun getArticle(
        @Url articleUrl: String,
        @Query("show-fields") fields: String
    ): ArticleDtoResponse
}
