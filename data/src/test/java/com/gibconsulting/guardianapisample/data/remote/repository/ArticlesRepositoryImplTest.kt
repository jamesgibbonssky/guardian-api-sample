package com.gibconsulting.guardianapisample.data.remote.repository

import com.gibconsulting.guardianapisample.data.remote.client.GuardianClient
import com.gibconsulting.guardianapisample.data.remote.mapper.ArticleDetailsMapper
import com.gibconsulting.guardianapisample.data.remote.mapper.ArticleMapper
import com.gibconsulting.guardianapisample.data.remote.model.ArticleDto
import com.gibconsulting.guardianapisample.data.remote.model.ArticleDtoContent
import com.gibconsulting.guardianapisample.data.remote.model.ArticleDtoListResponse
import com.gibconsulting.guardianapisample.data.remote.model.ArticleDtoResponse
import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.domain.model.ArticleDetails
import com.gibconsulting.guardianapisample.domain.repository.ArticlesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class ArticlesRepositoryImplTest {

    private val mockArticleDtoListResponse: ArticleDtoListResponse = mockk()
    private val mockArticleDto: ArticleDto = mockk()
    private val mockArticleDtoContent: ArticleDtoContent = mockk {
        every { content } returns mockArticleDto
    }
    private val mockArticleDtoResponse: ArticleDtoResponse = mockk {
        every { response } returns mockArticleDtoContent
    }
    private val guardianClient: GuardianClient = mockk {
        coEvery { searchArticles(EXPECTED_SEARCH_TERM) } returns mockArticleDtoListResponse
        coEvery { getArticle(TEST_URL, EXPECTED_FIELDS) } returns mockArticleDtoResponse
    }
    private val mockArticleList: List<Article> = mockk()
    private val articleMapper: ArticleMapper = mockk {
        every { map(mockArticleDtoListResponse) } returns mockArticleList
    }
    private val articleDetails: ArticleDetails = mockk()
    private val articleDetailsMapper: ArticleDetailsMapper = mockk {
        every { map(mockArticleDto) } returns articleDetails
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val cut: ArticlesRepository = ArticlesRepositoryImpl(
        guardianClient = guardianClient,
        articleMapper = articleMapper,
        articleDetailsMapper = articleDetailsMapper,
        dispatcher = UnconfinedTestDispatcher()
    )

    @Test
    fun `given articles available when getLatestArticles called then list of Article returned`() = runTest {
        // When
        val result = cut.getLatestArticles()

        // Then
        coVerify { guardianClient.searchArticles(EXPECTED_SEARCH_TERM) }
        verify { articleMapper.map(mockArticleDtoListResponse) }

        assertEquals(mockArticleList, result)
    }

    @Test
    fun `given articleUrl when getArticleDetails called the ArticleDetails returned`() = runTest {
        val result = cut.getArticleDetails(TEST_URL)

        coVerify { guardianClient.getArticle(TEST_URL, EXPECTED_FIELDS) }
        verify { articleDetailsMapper.map(mockArticleDto) }

        assertEquals(articleDetails, result)
    }

    companion object {
        private const val EXPECTED_SEARCH_TERM = "politics"
        private const val EXPECTED_FIELDS = "main,body,headline,thumbnail"
        private const val TEST_URL = "testUrl"
    }
}