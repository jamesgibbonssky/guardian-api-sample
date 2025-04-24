package com.gibconsulting.guardianapisample.data.remote.mapper

import com.gibconsulting.guardianapisample.data.remote.model.ArticleDto
import com.gibconsulting.guardianapisample.data.remote.model.ArticleDtoList
import com.gibconsulting.guardianapisample.data.remote.model.ArticleDtoListResponse
import com.gibconsulting.guardianapisample.data.remote.model.ArticleFieldsDto
import com.gibconsulting.guardianapisample.domain.model.Article
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date


class ArticleMapperTest {

    private val cut  = ArticleMapper()

    @Test
    fun `given ArticleDtoListResponse when map called then list of Article returned`() {

        val toBeMapped = ArticleDtoListResponse(
            response = ArticleDtoList(
                results = listOf(
                    ArticleDto(
                        id = TEST_ID,
                        sectionId = TEST_SECTION_ID,
                        sectionName = TEST_SECTION_NAME,
                        webPublicationDate = TEST_DATE,
                        webTitle = TEST_WEB_TITLE,
                        webUrl = TEST_WEB_URL,
                        apiUrl = TEST_API_URL,
                        fields = ArticleFieldsDto(
                            headline = TEST_HEADLINE,
                            main = TEST_MAIN,
                            body = TEST_BODY,
                            thumbnail = TEST_THUMBNAIL
                        )
                    )
                )
            )
        )
        val result = cut.map(toBeMapped)
        val expected = listOf(
            Article(
                id = TEST_ID,
                sectionId = TEST_SECTION_ID,
                sectionName = TEST_SECTION_NAME,
                published = TEST_DATE,
                title = TEST_HEADLINE,
                thumbnail = TEST_THUMBNAIL,
                url = TEST_API_URL
            )
        )
        assertEquals(expected, result)
    }

    companion object {
        private const val TEST_ID = "testId"
        private const val TEST_SECTION_ID = "testSectionId"
        private const val TEST_SECTION_NAME = "testSectionName"
        private const val TEST_WEB_TITLE = "testWebTitle"
        private const val TEST_WEB_URL = "testWebUrl"
        private const val TEST_API_URL = "testApiUrl"
        private val TEST_DATE = Date()
        private const val TEST_HEADLINE = "testHeadline"
        private const val TEST_MAIN = "testMain"
        private const val TEST_BODY = "testBody"
        private const val TEST_THUMBNAIL = "testThumbnail"
    }
}