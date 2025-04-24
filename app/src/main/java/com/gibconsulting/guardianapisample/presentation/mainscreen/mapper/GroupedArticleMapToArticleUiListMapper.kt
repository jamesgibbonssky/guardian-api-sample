package com.gibconsulting.guardianapisample.presentation.mainscreen.mapper

import com.gibconsulting.guardianapisample.domain.model.Article
import com.gibconsulting.guardianapisample.presentation.mainscreen.ArticleUiListItem
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase
import javax.inject.Inject

class GroupedArticleMapToArticleUiListMapper @Inject constructor(
    private val articleToArticleUiMapper: ArticleToArticleUiMapper
) {
    fun mapToUi(groupedArticles: Map<String, List<Article>>): List<ArticleUiListItem> {
        val items = mutableListOf<ArticleUiListItem>()
        groupedArticles.forEach { (label, articles) ->
            if (label == GetLatestArticlesGroupedUseCase.FAVOURITE) {
                items.add(index = 0, element = ArticleUiListItem.HeaderItem(label))
                items.addAll(
                    index = 1,
                    elements = articles.map {
                        ArticleUiListItem.ArticleItem(articleToArticleUiMapper.mapToUi(it))
                    }
                )
            } else {
                items.add(ArticleUiListItem.HeaderItem(label))
                items.addAll(
                    articles.map {
                        ArticleUiListItem.ArticleItem(articleToArticleUiMapper.mapToUi(it))
                    }
                )
            }
        }
        return items
    }
}
