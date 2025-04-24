package com.gibconsulting.guardianapisample.presentation.mainscreen.mapper

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.presentation.mainscreen.ArticleUi
import com.gibconsulting.guardianapisample.presentation.mainscreen.ArticleUiListItem
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class GroupedArticleMapToArticleUiListMapperTest {

    private val newestArticleItemUi: ArticleUi = mockk()
    private val secondNewestArticleItemUi: ArticleUi = mockk()
    private val thirdNewestArticleItemUi: ArticleUi = mockk()
    private val oldestArticleItemUi: ArticleUi = mockk()
    private val articleToArticleUiMapper: ArticleToArticleUiMapper = mockk {
        every { mapToUi(any()) } answers {
            val article = firstArg<Article>()
            when (article) {
                newestArticle -> newestArticleItemUi
                secondNewestArticle -> secondNewestArticleItemUi
                thirdNewestArticle -> thirdNewestArticleItemUi
                oldestArticle -> oldestArticleItemUi
                else -> throw IllegalArgumentException("Unknown article")
            }
        }
    }
    private val cut = GroupedArticleMapToArticleUiListMapper(
        articleToArticleUiMapper = articleToArticleUiMapper
    )

    @Test
    fun `given grouped article map when mapToUi called then list of article ui items returned`() {
        val result = cut.mapToUi(groupedArticles())

        val expected = listOf(
            ArticleUiListItem.HeaderItem(TEST_FAVOURITES_TITLE),
            ArticleUiListItem.ArticleItem(thirdNewestArticleItemUi),
            ArticleUiListItem.HeaderItem(TEST_NEWEST_DATE),
            ArticleUiListItem.ArticleItem(newestArticleItemUi),
            ArticleUiListItem.HeaderItem(TEST_SECOND_NEWEST_DATE),
            ArticleUiListItem.ArticleItem(secondNewestArticleItemUi),
            ArticleUiListItem.HeaderItem(TEST_OLDEST_DATE),
            ArticleUiListItem.ArticleItem(oldestArticleItemUi)
        )
        assertEquals(expected, result)
    }

    private fun groupedArticles(): Map<String, List<Article>> {
        return mapOf(
            TEST_FAVOURITES_TITLE to listOf(thirdNewestArticle),
            TEST_NEWEST_DATE to listOf(newestArticle),
            TEST_SECOND_NEWEST_DATE to listOf(secondNewestArticle),
            TEST_OLDEST_DATE to listOf(oldestArticle)
        )
    }

    private companion object {
        private const val TEST_FAVOURITES_TITLE = "Favourites"
        private const val TEST_NEWEST_DATE = "16-2025"
        private const val TEST_SECOND_NEWEST_DATE = "15-2025"
        private const val TEST_OLDEST_DATE = "13-2025"
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