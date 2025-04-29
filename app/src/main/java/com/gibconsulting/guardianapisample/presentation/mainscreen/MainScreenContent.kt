package com.gibconsulting.guardianapisample.presentation.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.gibconsulting.guardianapisample.R
import com.gibconsulting.guardianapisample.presentation.mainscreen.previewmocks.articleListPreviewMock
import com.gibconsulting.guardianapisample.presentation.theme.AppBlack
import com.gibconsulting.guardianapisample.presentation.theme.AppLightGray
import com.gibconsulting.guardianapisample.presentation.theme.AppWhite
import com.gibconsulting.guardianapisample.presentation.theme.GuardianApiSampleAppTheme
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase.Companion.WEEK_DATE_PATTERN
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    state: MainScreenViewState,
    modifier: Modifier = Modifier,
    onItemClicked: (id: String) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val localisedArticles = state.articles.articlesWithLocalizedHeaders()
    PullToRefreshBox(
        isRefreshing = state.loading,
        onRefresh = onRefresh,
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        if (state.articles.isEmpty() && !state.loading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()) {
                Text(
                    text = stringResource(R.string.no_data),
                    style = MaterialTheme.typography.titleMedium,
                    color = AppBlack
                )
                Button(onClick = onRefresh) {
                    Text(text = stringResource(R.string.refresh))
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(8.dp)
            ) {
                items(
                    localisedArticles,
                    key = {
                        when (it) {
                            is ArticleUiListItem.ArticleItem -> it.article.id
                            is ArticleUiListItem.HeaderItem -> it.header
                        }
                    }
                ) {
                    when (it) {
                        is ArticleUiListItem.HeaderItem -> {
                            HorizontalDivider(
                                color = AppBlack,
                                thickness = 2.dp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = it.header,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                ),
                                textAlign = TextAlign.Start
                            )
                        }

                        is ArticleUiListItem.ArticleItem -> {
                            HorizontalDivider(
                                color = AppLightGray,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            ArticleItem(
                                article = it.article,
                                modifier = Modifier
                                    .background(AppWhite)
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                onItemClicked = onItemClicked
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun List<ArticleUiListItem>.articlesWithLocalizedHeaders(): List<ArticleUiListItem> {
    val dateFormat = SimpleDateFormat(WEEK_DATE_PATTERN, Locale.getDefault())
    val now = Calendar.getInstance()
    val nowAsWeekLabel = dateFormat.format(now.time)
    val lastWeek = now.apply { add(Calendar.WEEK_OF_YEAR, -1) }
    val lastWeekAsWeekLabel = dateFormat.format(lastWeek.time)

    return this.map {
        if (it is ArticleUiListItem.HeaderItem) {
            if (it.header == GetLatestArticlesGroupedUseCase.FAVOURITE) {
                it.copy(header = stringResource(R.string.favourites_title))
            } else {
                if (it.header == nowAsWeekLabel) {
                    it.copy(header = stringResource(R.string.this_week_label))
                } else if(it.header == lastWeekAsWeekLabel) {
                    it.copy(header = stringResource(R.string.last_week_label))
                } else {
                    it.copy(header = "${stringResource(R.string.week_label)}: ${it.header}")
                }
            }
        } else {
            it
        }
    }
}

@Composable
fun ArticleItem(
    article: ArticleUi,
    modifier: Modifier = Modifier,
    onItemClicked: (id: String) -> Unit = {}) {
    Row(verticalAlignment = Alignment.Top,
        modifier = modifier
            .wrapContentHeight()
            .clickable { onItemClicked(article.id) }
    ) {
        AsyncImage(
            model = article.thumbnail,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(percent = 10))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${stringResource(R.string.published_label)}: ${article.published}",
                style = MaterialTheme.typography.labelMedium
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenContentWithArticlesPreview() {
    GuardianApiSampleAppTheme {
        MainScreenContent(
            state = MainScreenViewState(
                articles = articleListPreviewMock,
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenContentLoadingPreview() {
    GuardianApiSampleAppTheme {
        MainScreenContent(
            state = MainScreenViewState(
                loading = true
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenContentNoDataPreview() {
    GuardianApiSampleAppTheme {
        MainScreenContent(
            state = MainScreenViewState(
                loading = false,
                articles = listOf()
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}