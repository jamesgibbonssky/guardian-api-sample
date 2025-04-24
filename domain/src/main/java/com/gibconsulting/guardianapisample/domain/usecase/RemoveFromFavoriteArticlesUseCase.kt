package com.gibconsulting.guardianapisample.domain.usecase

interface RemoveFromFavoriteArticlesUseCase {
    suspend fun invoke(id: String)
}