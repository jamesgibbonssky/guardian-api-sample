package com.gibconsulting.guardianapisample.presentation.detailscreen.mapper

import com.gibconsulting.guardianapisample.domain.model.ArticleDetails
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleDetailsToArticleDetailsUiMapperTest {

    private val cut = ArticleDetailsToArticleDetailsUiMapper()

    @Test
    fun `given article details when mapToUi then article details ui returned`() {
        // Given
        val toBeMapped = ArticleDetails(
            id = TEST_ARTICLE_ID,
            headline = TEST_ARTICLE_HEADLINE,
            body = TEST_ARTICLE_BODY,
            imageUrl = TEST_ARTICLE_IMAGE_URL
        )

        // When
        val result = cut.mapToUi(toBeMapped)

        // Then
        assertEquals(TEST_ARTICLE_ID, result.id)
        assertEquals(TEST_ARTICLE_HEADLINE, result.headline)
        assertEquals(TEST_ARTICLE_BODY, result.body)
        assertEquals(TEST_ARTICLE_IMAGE_URL, result.imageUrl)
    }

    private companion object {
        private const val TEST_ARTICLE_ID = "1"
        private const val TEST_ARTICLE_HEADLINE = "Test Headline"
        private const val TEST_ARTICLE_BODY = "Test Body"
        private const val TEST_ARTICLE_IMAGE_URL = "http://example.com/image.jpg"
    }
}