package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.model.ArticleDetails
import com.gibconsulting.guardianapisample.domain.repository.ArticlesRepository
import com.gibconsulting.guardianapisample.domain.usecase.GetArticleDetailsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetArticleDetailsUseCaseImplTest {

    private val mockArticleDetails: ArticleDetails = mockk()
    private val articlesRepository: ArticlesRepository = mockk {
        coEvery { getArticleDetails(any()) } returns mockArticleDetails
    }
    private val cut: GetArticleDetailsUseCase = GetArticleDetailsUseCaseImpl(
        articlesRepository = articlesRepository
    )

    @Test
    fun `given article id when use case invoked then article details retrieved from repository and returned`() = runTest {
        // Given
        val articleId = "testId"

        // When
        val result = cut.invoke(articleId)

        // Then
        coVerify { articlesRepository.getArticleDetails(articleId) }
        assertEquals(mockArticleDetails, result)
    }
}