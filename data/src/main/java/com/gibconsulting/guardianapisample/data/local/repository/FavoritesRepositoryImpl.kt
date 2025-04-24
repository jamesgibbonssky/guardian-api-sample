package com.gibconsulting.guardianapisample.data.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.gibconsulting.guardianapisample.data.local.di.FavoritesDataStorePreferences
import com.gibconsulting.guardianapisample.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FavoritesRepositoryImpl @Inject constructor(
    @FavoritesDataStorePreferences private val preferences: DataStore<Preferences>
) : FavoritesRepository {

    override fun isFavourite(id: String): Flow<Boolean> {
        return getAllFavourites()
            .map { it.contains(id) }
            .distinctUntilChanged()
    }

    override suspend fun addFavourite(id: String) {
        preferences.edit {
            it[KeyFavourites] = (it[KeyFavourites] ?: emptySet()).toMutableSet().apply {
                add(id)
            }.toSet()
        }
    }

    override suspend fun removeFavourite(id: String) {
        preferences.edit {
            it[KeyFavourites] = (it[KeyFavourites] ?: emptySet()).toMutableSet().apply {
                remove(id)
            }.toSet()
        }
    }

    override fun getAllFavourites(): Flow<Set<String>> {
        return preferences.data
            .map { it[KeyFavourites] ?: emptySet() }
            .distinctUntilChanged()
    }

    companion object {
        private val KeyFavourites = stringSetPreferencesKey("favourites")
    }
}