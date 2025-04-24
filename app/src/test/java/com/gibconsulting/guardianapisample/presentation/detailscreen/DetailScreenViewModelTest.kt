package com.gibconsulting.guardianapisample.presentation.detailscreen

import app.cash.turbine.test
import com.gibconsulting.guardianapisample.core.TestCoroutineRule
import com.gibconsulting.guardianapisample.domain.model.ArticleDetails
import com.gibconsulting.guardianapisample.domain.usecase.AddToFavoriteArticlesUseCase
import com.gibconsulting.guardianapisample.domain.usecase.GetArticleDetailsUseCase
import com.gibconsulting.guardianapisample.domain.usecase.IsFavoriteArticleUseCase
import com.gibconsulting.guardianapisample.domain.usecase.RemoveFromFavoriteArticlesUseCase
import com.gibconsulting.guardianapisample.presentation.detailscreen.mapper.ArticleDetailsToArticleDetailsUiMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class DetailScreenViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val rule = TestCoroutineRule()

    private val mockArticleDetails: ArticleDetails = mockk()
    private val getArticleDetailsUseCase: GetArticleDetailsUseCase = mockk {
        coEvery { this@mockk.invoke(any()) } returns mockArticleDetails
    }
    private val addToFavoriteArticlesUseCase: AddToFavoriteArticlesUseCase = mockk(relaxed = true)
    private val removeFromFavoriteArticlesUseCase: RemoveFromFavoriteArticlesUseCase = mockk(relaxed = true)
    private val mutableSharedFlow = MutableSharedFlow<Boolean>()
    private val isFavoriteArticleUseCase: IsFavoriteArticleUseCase = mockk {
        coEvery { this@mockk.invoke(any()) } returns mutableSharedFlow
    }
    private val mockArticleDetailsUi: ArticleDetailsUi = mockk()
    private val articleDetailsToArticleDetailsUiMapper: ArticleDetailsToArticleDetailsUiMapper = mockk {
        every { mapToUi(mockArticleDetails) } returns mockArticleDetailsUi
    }
    private val cut = DetailScreenViewModel(
        getArticleDetailsUseCase = getArticleDetailsUseCase,
        addToFavoriteArticlesUseCase = addToFavoriteArticlesUseCase,
        removeFromFavoriteArticlesUseCase = removeFromFavoriteArticlesUseCase,
        isFavoriteArticleUseCase = isFavoriteArticleUseCase,
        articleDetailsToArticleDetailsUiMapper = articleDetailsToArticleDetailsUiMapper
    )

    @Test
    fun `when class instantiated then initial state is loading`() = runTest {
        // When
        val localCut = DetailScreenViewModel(
            getArticleDetailsUseCase = getArticleDetailsUseCase,
            addToFavoriteArticlesUseCase = addToFavoriteArticlesUseCase,
            removeFromFavoriteArticlesUseCase = removeFromFavoriteArticlesUseCase,
            isFavoriteArticleUseCase = isFavoriteArticleUseCase,
            articleDetailsToArticleDetailsUiMapper = articleDetailsToArticleDetailsUiMapper
        )

        // Then
        localCut.uiState.value.let {
            assert(it.loading)
        }
    }

    @Test
    fun `when handleEvent called with OnDetailScreenDisplayed then article details with favourite state sent to ui`() = runTest {
        // Given
        val articleId = "articleId"

        cut.uiState.test {
            // When
            cut.handleEvent(DetailScreenEvent.OnDetailScreenDisplayed(articleId))

            // Then
            coVerify {
                getArticleDetailsUseCase.invoke(articleId)
            }
            verify {
                articleDetailsToArticleDetailsUiMapper.mapToUi(mockArticleDetails)
            }
            coVerify {
                isFavoriteArticleUseCase.invoke(articleId)
            }

            val state0 = awaitItem()
            assertEquals(true, state0.loading)

            val state1 = awaitItem()
            assertEquals(mockArticleDetailsUi, state1.article)
            assertEquals(false, state1.loading )

            mutableSharedFlow.emit(true)
            val state2 = awaitItem()
            assertEquals(true, state2.isFavorite)
        }
    }

    @Test
    fun `given handleEvent called with OnDetailScreenDisplayed when getArticleDetailsUseCase throws exception then view effect sent to ui`() = runTest {
        // Given
        coEvery { getArticleDetailsUseCase.invoke(any()) } throws Exception("Error")
        val articleId = "articleId"

        // When
        cut.handleEvent(DetailScreenEvent.OnDetailScreenDisplayed(articleId))

        // Then
        cut.viewEffect.first().let {
            assert(it is DetailScreenViewEffect.ShowError)
            assert((it as DetailScreenViewEffect.ShowError).messageId > 0)
        }
    }

    @Test
    fun `when handleEvent called with OnBackClicked then navigate back`() = runTest {
        // When
        cut.handleEvent(DetailScreenEvent.OnBackClicked)

        // Then
        cut.navigationEffect.first().let {
            assert(it is DetailScreenNavigationEffect.NavigateBack)
        }
    }

    @Test
    fun `given article is favourite when handleEvent called with OnFavoriteClicked then remove article from favourites`() = runTest {
        // Given
        val articleId = "articleId"
        every { mockArticleDetailsUi.id } returns articleId
        coEvery { isFavoriteArticleUseCase.invoke(any()) } returns flowOf(true)
        cut.handleEvent(DetailScreenEvent.OnDetailScreenDisplayed(articleId))

        // When
        cut.handleEvent(DetailScreenEvent.OnFavoriteClicked)

        // Then
        coVerify {
            removeFromFavoriteArticlesUseCase.invoke(articleId)
        }
    }

    @Test
    fun `given article is not favourite when handleEvent called with OnFavoriteClicked then add article to favourites`() = runTest {
        // Given
        val articleId = "articleId"
        every { mockArticleDetailsUi.id } returns articleId
        coEvery { isFavoriteArticleUseCase.invoke(any()) } returns flowOf(false)
        cut.handleEvent(DetailScreenEvent.OnDetailScreenDisplayed(articleId))

        // When
        cut.handleEvent(DetailScreenEvent.OnFavoriteClicked)

        // Then
        coVerify {
            addToFavoriteArticlesUseCase.invoke(articleId)
        }
    }
}