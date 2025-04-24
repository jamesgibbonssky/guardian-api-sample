package com.gibconsulting.guardianapisample.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun isFavourite(id: String): Flow<Boolean>
    suspend fun addFavourite(id: String)
    suspend fun removeFavourite(id: String)
    fun getAllFavourites(): Flow<Set<String>>
}