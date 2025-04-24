package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.repository.FavoritesRepository
import com.gibconsulting.guardianapisample.domain.usecase.RemoveFromFavoriteArticlesUseCase
import javax.inject.Inject

internal class RemoveFromFavoriteArticlesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : RemoveFromFavoriteArticlesUseCase {
    override suspend fun invoke(id: String) {
        favoritesRepository.removeFavourite(id)
    }
}