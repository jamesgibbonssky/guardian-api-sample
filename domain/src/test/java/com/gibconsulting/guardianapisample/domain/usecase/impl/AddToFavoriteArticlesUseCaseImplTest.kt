package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.repository.FavoritesRepository
import com.gibconsulting.guardianapisample.domain.usecase.AddToFavoriteArticlesUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class AddToFavoriteArticlesUseCaseImplTest {

    private val favoritesRepository: FavoritesRepository = mockk(relaxed = true)
    private val cut: AddToFavoriteArticlesUseCase = AddToFavoriteArticlesUseCaseImpl(
        favoritesRepository = favoritesRepository
    )

    @Test
    fun `given article id when use case invoked then id is added to favorites repository`() = runTest {
        // Given
        val id = "article_id"

        // When
        cut.invoke(id)

        // Then
        coVerify { favoritesRepository.addFavourite(id) }
    }
}