package com.gibconsulting.guardianapisample.domain.usecase.impl

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase.Companion.FAVOURITE
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase.Companion.WEEK_DATE_PATTERN
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesUseCase
import com.gibconsulting.guardianapisample.domain.usecase.IsFavoriteArticleUseCase
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

internal class GetLatestArticlesGroupedUseCaseImpl @Inject constructor(
    private val getLatestArticlesUseCase: GetLatestArticlesUseCase,
    private val isFavoriteArticleUseCase: IsFavoriteArticleUseCase
) : GetLatestArticlesGroupedUseCase {

    override suspend fun invoke(): Map<String, List<Article>> {
        val articles = getLatestArticlesUseCase.invoke()
        return groupArticlesByWeek(articles)
    }

    private suspend fun groupArticlesByWeek(articles: List<Article>): Map<String, List<Article>> {
        val dateFormat = SimpleDateFormat(WEEK_DATE_PATTERN, Locale.getDefault())
        return articles.groupBy { article ->
            if (isFavoriteArticleUseCase.invoke(article.id).first()) {
                FAVOURITE
            } else {
                val calendar = Calendar.getInstance().apply {
                    time = article.published
                }
                dateFormat.format(calendar.time)
            }
        }
    }
}
