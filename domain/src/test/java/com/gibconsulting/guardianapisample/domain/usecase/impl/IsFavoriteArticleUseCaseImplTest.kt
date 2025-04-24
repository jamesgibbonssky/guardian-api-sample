package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.repository.FavoritesRepository
import com.gibconsulting.guardianapisample.domain.usecase.IsFavoriteArticleUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class IsFavoriteArticleUseCaseImplTest {

    private val expectedIsFavoriteFlowValues = listOf(false, true, false)
    private val favoritesRepository: FavoritesRepository = mockk {
        coEvery { isFavourite(any()) } returns expectedIsFavoriteFlowValues.asFlow()
    }
    private val cut: IsFavoriteArticleUseCase = IsFavoriteArticleUseCaseImpl(
        favoritesRepository = favoritesRepository
    )

    @Test
    fun `given article id when use case invoked then flow of favorite state changes from repository is returned`() = runTest {
        // Given
        val testId = "testId"

        // When
        val result = cut.invoke(testId).toList()

        // Then
        assertEquals(expectedIsFavoriteFlowValues, result)
    }
}