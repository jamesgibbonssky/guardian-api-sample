package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.domain.repository.ArticlesRepository
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date


class GetLatestArticlesUseCaseImplTest {

    private val articlesRepository: ArticlesRepository = mockk {
        coEvery { getLatestArticles() } returns articlesListUnsorted()
    }
    private val cut: GetLatestArticlesUseCase = GetLatestArticlesUseCaseImpl(
        articlesRepository = articlesRepository
    )

    @Test
    fun `given articles available when use case invoked then articles retrieved from repository and returned sorted in descending date`() = runTest {
        // When
       val result = cut.invoke()

        // Then
        val expected = listOf(newestArticle, secondNewestArticle, oldestArticle)
        coVerify { articlesRepository.getLatestArticles() }
        assertEquals(expected, result)
    }

    private fun articlesListUnsorted(): List<Article> {
        return listOf(
            oldestArticle,
            newestArticle,
            secondNewestArticle
        )
    }

    companion object {
        private val newestArticle = Article(
            id = "id2",
            thumbnail = "thumbnail2",
            sectionId = "sectionId2",
            sectionName = "sectionName2",
            published = Date(1745150400000), // April 20, 2025 12:00:00 PM
            title = "title2",
            url = "url2",
        )
        private val secondNewestArticle = Article(
            id = "id3",
            thumbnail = "thumbnail3",
            sectionId = "sectionId3",
            sectionName = "sectionName3",
            published = Date(1745064000000), // April 19, 2025 12:00:00 PM
            title = "title3",
            url = "url3",
        )
        private val oldestArticle = Article(
            id = "id1",
            thumbnail = "thumbnail1",
            sectionId = "sectionId1",
            sectionName = "sectionName1",
            published = Date(1744977600000), // April 18, 2025 12:00:00 PM
            title = "title1",
            url = "url1",
        )
    }
}