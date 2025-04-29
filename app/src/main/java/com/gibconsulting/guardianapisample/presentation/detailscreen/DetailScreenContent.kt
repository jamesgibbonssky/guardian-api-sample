package com.gibconsulting.guardianapisample.presentation.detailscreen

import android.text.Spanned
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil3.compose.AsyncImage
import com.gibconsulting.guardianapisample.R
import com.gibconsulting.guardianapisample.presentation.theme.GuardianApiSampleAppTheme
import timber.log.Timber

@Composable
fun DetailScreenContent(
    state: DetailScreenViewState,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (state.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp),
                color = MaterialTheme.colorScheme.tertiary,
            )
        } else {
            Timber.d("JGG DetailScreenContent: state.article: ${state.article?.imageUrl}")
            state.article?.let {
                Column(modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
                    .wrapContentHeight()
                ) {
                    AsyncImage(
                        model = it.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        modifier = Modifier
                            .height(230.dp)
                    )
                    Text(
                        text = it.headline,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                    )
                    HtmlText(
                        text = it.body,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
                    )
                }
            }
        }
    }
}

fun String.toHtml(): String {
    val spanned: Spanned = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
    return spanned.toString()
}

@Composable
fun HtmlText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text.toHtml(),
        modifier = modifier,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
fun DetailScreenContentPreview() {
    GuardianApiSampleAppTheme {
        DetailScreenContent(
            state = DetailScreenViewState(
                loading = false,
                article = ArticleDetailsUi(
                    id = "1",
                    headline = "UK politics: No 10 ‘confident’ on securing supplies to keep Scunthorpe furnaces burning – as it happened",
                    body = "<p>" +
                            "The UK government is confident it can secure supplies of gas to keep the Scunthorpe furnaces burning, despite the company’s warning that it may have to shut down production if it cannot secure supplies of gas.<br>" +
                            "</p>",
                    imageUrl = ""
                )
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}