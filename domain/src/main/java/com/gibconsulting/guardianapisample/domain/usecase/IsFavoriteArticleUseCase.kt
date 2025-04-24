package com.gibconsulting.guardianapisample.domain.usecase

import kotlinx.coroutines.flow.Flow

interface IsFavoriteArticleUseCase {
    fun invoke(id: String): Flow<Boolean>
}