package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.repository.FavoritesRepository
import com.gibconsulting.guardianapisample.domain.usecase.RemoveFromFavoriteArticlesUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class RemoveFromFavoriteArticlesUseCaseImplTest {

    private val favoritesRepository: FavoritesRepository = mockk(relaxed = true)
    private val cut: RemoveFromFavoriteArticlesUseCase = RemoveFromFavoriteArticlesUseCaseImpl(
        favoritesRepository = favoritesRepository
    )

    @Test
    fun `given article id when use case invoked then request repository to remove article id`() = runTest {
        // Given
        val testArticleId = "testId"

        // When
        cut.invoke(testArticleId)

        // Then
        coVerify { favoritesRepository.removeFavourite(testArticleId) }
    }
}