package com.gibconsulting.guardianapisample.presentation.mainscreen.mapper

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.presentation.mainscreen.ArticleUi
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ArticleToArticleUiMapper @Inject constructor() {
    fun mapToUi(article: Article): ArticleUi {
        return ArticleUi(
            id = article.id,
            thumbnail = article.thumbnail,
            published = article.publishedDateFormatted(),
            title = article.title
        )
    }

    fun Article.publishedDateFormatted(): String {
        return SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(published)
    }

    companion object {
        private const val DATE_FORMAT = "dd-MM-yyyy"
    }
}