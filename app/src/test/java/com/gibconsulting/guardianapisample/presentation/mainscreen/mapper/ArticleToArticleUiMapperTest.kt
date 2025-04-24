package com.gibconsulting.guardianapisample.presentation.mainscreen.mapper

import com.gibconsulting.guardianapisample.domain.model.Article
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class ArticleToArticleUiMapperTest {

    private val cut = ArticleToArticleUiMapper()

    @Test
    fun `given article when mapToUi called then mapped article ui returned`() {
        // Given
        val toBeMapped = Article(
            id = TEST_ARTICLE_ID,
            thumbnail = TEST_ARTICLE_THUMBNAIL,
            published = TEST_ARTICLE_PUBLISHED,
            title = TEST_ARTICLE_TITLE,
            sectionId = TEST_SECTION_ID,
            sectionName = TEST_SECTION_NAME,
            url = TEST_URL
        )

        // When
        val result = cut.mapToUi(toBeMapped)

        // Then
        assertEquals(TEST_ARTICLE_ID, result.id)
        assertEquals(TEST_ARTICLE_THUMBNAIL, result.thumbnail)
        assertEquals(EXPECTED_ARTICLE_PUBLISHED, result.published)
        assertEquals(TEST_ARTICLE_TITLE, result.title)
    }

    private companion object {
        private const val TEST_ARTICLE_ID = "1"
        private const val TEST_ARTICLE_THUMBNAIL = "http://example.com/thumbnail.jpg"
        private val TEST_ARTICLE_PUBLISHED = Date(1745150400000) //  April 20, 2025 12:00:00 PM
        private const val EXPECTED_ARTICLE_PUBLISHED = "20-04-2025"
        private const val TEST_ARTICLE_TITLE = "Test Title"
        private const val TEST_SECTION_ID = "sectionId"
        private const val TEST_SECTION_NAME = "sectionName"
        private const val TEST_URL = "http://example.com/article"
    }
}