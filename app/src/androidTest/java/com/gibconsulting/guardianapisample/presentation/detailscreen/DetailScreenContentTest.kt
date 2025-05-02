package com.gibconsulting.guardianapisample.presentation.detailscreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.gibconsulting.guardianapisample.presentation.theme.GuardianApiSampleAppTheme
import org.junit.Rule
import org.junit.Test

class DetailScreenContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun givenStateIsLoadingWhenDetailScreenShownThenProgressIndicatorShow() {
        // Given
        val testState = DetailScreenViewState(
            loading = true
        )

        // When
        composeTestRule.setContent {
            GuardianApiSampleAppTheme {
                DetailScreenContent(
                    state = testState
                )
            }
        }

        // Then
        composeTestRule.onNodeWithTag("loadingProgressIndicator").assertIsDisplayed()
    }

    @Test
    fun givenStateHasArticleWhenDetailScreenShownThenArticleDetailsDisplayed() {
        // Given
        val testState = DetailScreenViewState(
            article = ArticleDetailsUi(
                id = "id",
                headline = "headline",
                body = "articleBody",
                imageUrl = "url"
            ),
            isFavorite = false
        )

        // When
        composeTestRule.setContent {
            GuardianApiSampleAppTheme {
                DetailScreenContent(
                    state = testState
                )
            }
        }

        // Then
        composeTestRule.onNodeWithTag("articleImage").assertIsDisplayed()
        composeTestRule.onNodeWithText("headline").assertIsDisplayed()
        composeTestRule.onNodeWithText("articleBody").assertIsDisplayed()
    }
}