package com.gibconsulting.guardianapisample.presentation.mainscreen

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase
import com.gibconsulting.guardianapisample.presentation.mainscreen.mapper.GroupedArticleMapToArticleUiListMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MainScreenViewModelTest {

    private val mockGroupedArticlesMap: Map<String, List<Article>> = mockk()
    private val getLatestArticlesGroupedUseCase: GetLatestArticlesGroupedUseCase = mockk {
        coEvery { this@mockk.invoke() } returns mockGroupedArticlesMap
    }
    private val articleUiList: List<ArticleUiListItem> = mockk()
    private val groupedArticleMapToArticleUiListMapper: GroupedArticleMapToArticleUiListMapper = mockk {
        every { mapToUi(mockGroupedArticlesMap) } returns articleUiList
    }

    private val cut = MainScreenViewModel(
        getLatestArticlesUseCase = getLatestArticlesGroupedUseCase,
        groupedArticleMapToArticleUiListMapper = groupedArticleMapToArticleUiListMapper
    )

    @Test
    fun `when class instantiated then get articles and update state`() = runTest {
        val cut = MainScreenViewModel(
            getLatestArticlesUseCase = getLatestArticlesGroupedUseCase,
            groupedArticleMapToArticleUiListMapper = groupedArticleMapToArticleUiListMapper
        )

        coVerify { getLatestArticlesGroupedUseCase.invoke() }
        coVerify { groupedArticleMapToArticleUiListMapper.mapToUi(mockGroupedArticlesMap) }

        cut.uiState.value.let {
            assert(it.articles == articleUiList)
            assert(!it.loading)
        }
    }

    @Test
    fun `given articles requested when exception thrown then view effect sent to ui `() = runTest{
        // Given
        coEvery { getLatestArticlesGroupedUseCase.invoke() } throws Exception("Error")

        // When
        val cut = MainScreenViewModel(
            getLatestArticlesUseCase = getLatestArticlesGroupedUseCase,
            groupedArticleMapToArticleUiListMapper = groupedArticleMapToArticleUiListMapper
        )

        // Then
        cut.viewEffect.first().let {
            assert(it is MainScreenViewEffect.ShowError)
            assert((it as MainScreenViewEffect.ShowError).messageId > 0)
        }

    }

    @Test
    fun `when handleEvent called with OnClicked then navigate `() = runTest {
        val articleId = ""
        cut.handleEvent(MainScreenEvent.OnClicked(articleId))

        cut.navigationEffect.first().let {
            assert(it is MainScreenNavigationEffect.NavigateToDetailScreen)
            assert((it as MainScreenNavigationEffect.NavigateToDetailScreen).articleId == articleId)
        }
    }

    @Test
    fun `when handleEvent called with Refresh then refresh articles `() = runTest {

        // When
        cut.handleEvent(MainScreenEvent.OnRefresh)

        // Then
        coVerify { getLatestArticlesGroupedUseCase.invoke() }
        coVerify { groupedArticleMapToArticleUiListMapper.mapToUi(mockGroupedArticlesMap) }

        cut.uiState.value.let {
            assert(it.articles == articleUiList)
            assert(!it.loading)
        }
    }
}