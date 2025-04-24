package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.repository.FavoritesRepository
import com.gibconsulting.guardianapisample.domain.usecase.AddToFavoriteArticlesUseCase
import javax.inject.Inject

internal class AddToFavoriteArticlesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : AddToFavoriteArticlesUseCase {
    override suspend fun invoke(id: String) {
        favoritesRepository.addFavourite(id)
    }
}