package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.repository.FavoritesRepository
import com.gibconsulting.guardianapisample.domain.usecase.IsFavoriteArticleUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class IsFavoriteArticleUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : IsFavoriteArticleUseCase {
    override fun invoke(id: String): Flow<Boolean> {
        return favoritesRepository.isFavourite(id)
    }
}