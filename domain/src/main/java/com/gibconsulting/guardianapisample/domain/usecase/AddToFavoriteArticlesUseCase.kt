package com.gibconsulting.guardianapisample.domain.usecase

interface AddToFavoriteArticlesUseCase {
    suspend fun invoke(id: String)
}