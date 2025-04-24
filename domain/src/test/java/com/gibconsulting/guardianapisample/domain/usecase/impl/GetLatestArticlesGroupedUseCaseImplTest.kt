package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class GetLatestArticlesGroupedUseCaseImplTest {

    private val getLatestArticlesUseCase: GetLatestArticlesUseCase = mockk {
        coEvery { this@mockk.invoke() } returns articlesListSortedDescendingDateOrder()
    }
    private val isFavoriteArticleUseCase: IsFavoriteArticleUseCaseImpl = mockk {
        coEvery { this@mockk.invoke(any()) } answers {
            val articleId = firstArg<String>()
            if (articleId == thirdNewestArticle.id) {
                flowOf(true)
            } else {
                flowOf(false)
            }
        }
    }
    private val cut: GetLatestArticlesGroupedUseCase = GetLatestArticlesGroupedUseCaseImpl(
        getLatestArticlesUseCase = getLatestArticlesUseCase,
        isFavoriteArticleUseCase = isFavoriteArticleUseCase
    )
    @Test
    fun `when use case invoked then articles grouped by favourites and date returned`() = runTest {

        // When
        val result = cut.invoke()

        // Then
        val expected = mapOf(
            "Favourites" to listOf(thirdNewestArticle),
            "16-2025" to listOf(newestArticle),
            "15-2025" to listOf(secondNewestArticle),
            "13-2025" to listOf(oldestArticle)
        )
        assertEquals(expected, result)
    }

    private fun articlesListSortedDescendingDateOrder(): List<Article> {
        return listOf(
            newestArticle,
            secondNewestArticle,
            thirdNewestArticle,
            oldestArticle
        )
    }

    companion object {
        private val newestArticle = Article(
            id = "id1",
            thumbnail = "thumbnail1",
            sectionId = "sectionId2",
            sectionName = "sectionName1",
            published = Date(1745150400000), //  April 20, 2025 12:00:00 PM
            title = "title1",
            url = "url1",
        )
        private val secondNewestArticle = Article(
            id = "id2",
            thumbnail = "thumbnail2",
            sectionId = "sectionId2",
            sectionName = "sectionName2",
            published = Date(1744545600000), // April 13, 2025 12:00:00 PM
            title = "title2",
            url = "url2",
        )
        private val thirdNewestArticle = Article(
            id = "id3",
            thumbnail = "thumbnail3",
            sectionId = "sectionId3",
            sectionName = "sectionName3",
            published = Date(1743940800000), // April 6, 2025 12:00:00 PM
            title = "title3",
            url = "url3",
        )
        private val oldestArticle = Article(
            id = "id4",
            thumbnail = "thumbnail4",
            sectionId = "sectionId4",
            sectionName = "sectionName4",
            published = Date(1743336000000), // March 30, 2025 12:00:00 PM
            title = "title4",
            url = "url4",
        )
    }
}