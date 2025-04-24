package com.gibconsulting.guardianapisample.data.remote.mapper

import com.gibconsulting.guardianapisample.data.remote.model.ArticleDto
import com.gibconsulting.guardianapisample.data.remote.model.ArticleFieldsDto
import com.gibconsulting.guardianapisample.domain.model.ArticleDetails
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class ArticleDetailsMapperTest {

    private val cut = ArticleDetailsMapper()

    @Test
    fun `given ArticleDto when map called then mapped Article returned`() {
        // Given
        val toBeMapped = ArticleDto(
            id = TEST_ID,
            sectionId = TEST_SECTION_ID,
            sectionName = TEST_SECTION_NAME,
            webPublicationDate = testPublished,
            webTitle = TEST_WEB_TITLE,
            webUrl = TEST_WEB_URL,
            apiUrl = TEST_API_URL,
            fields = ArticleFieldsDto(
                headline = TEST_HEADLINE,
                body = TEST_BODY,
                thumbnail = TEST_THUMBNAIL,
                main = TEST_MAIN
            )
        )

        // When
        val result = cut.map(toBeMapped)

        // Then
        val expected = ArticleDetails(
            id = TEST_ID,
            headline = TEST_HEADLINE,
            body = TEST_BODY,
            imageUrl = TEST_THUMBNAIL
        )
        assertEquals(expected, result)
    }


    companion object {
        private const val TEST_ID = "testId"
        private const val TEST_HEADLINE = "testHeadline"
        private const val TEST_BODY = "testBody"
        private const val TEST_THUMBNAIL = "testThumbnail"

        private const val TEST_MAIN = "testMain"
        private const val TEST_SECTION_ID = "testSectionId"
        private const val TEST_SECTION_NAME = "testSectionName"
        private val testPublished = Date()
        private const val TEST_WEB_TITLE = "testWebTitle"
        private const val TEST_WEB_URL = "testWebUrl"
        private const val TEST_API_URL = "testApiUrl"
    }
}