package com.gibconsulting.guardianapisample.data.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.gibconsulting.guardianapisample.data.local.repository.FavoritesRepositoryImpl
import com.gibconsulting.guardianapisample.domain.repository.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class FavoritesDataStorePreferences

private const val FavoritesPreferencesName = "favorites_prefs"

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {
    @Binds
    internal abstract fun providesFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository

    companion object {
        @FavoritesDataStorePreferences
        @Singleton
        @Provides
        internal fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = { context.preferencesDataStoreFile(FavoritesPreferencesName) },
            )
        }
    }
}