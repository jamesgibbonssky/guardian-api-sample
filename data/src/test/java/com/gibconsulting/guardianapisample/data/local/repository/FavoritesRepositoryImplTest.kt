package com.gibconsulting.guardianapisample.data.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.gibconsulting.guardianapisample.domain.repository.FavoritesRepository
import io.mockk.coEvery
import io.mockk.every
import org.junit.Test
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.rules.TemporaryFolder

class FavoritesRepositoryImplTest {
    @get:Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder.builder()
        .assureDeletion()
        .build()

    private val preferences = mockk<DataStore<Preferences>>(relaxed = true).apply {
        coEvery { data } returns flowOf(mockPreferences1(), mockPreferences2(), mockPreferences3(), mockPreferences4())
    }

    private val cut: FavoritesRepository = FavoritesRepositoryImpl(preferences)

    @Test
    fun `given articleId when isFavourite called then flow of booleans returned indicating whether the article is a favourite`() = runTest {
        val articleId = "testId1"

        val result = cut.isFavourite(articleId).toList()

        val expected = listOf(true, false, true)
        assertEquals(expected, result)
    }

    @Test
    fun `given data store is empty when addFavourite called with assetId then assetId added as favourite`() = runTest {
        // Given
        val (localCut, dataStore) = buildCutWithDatastoreBackedWithTempFile(backgroundScope)

        // When
        localCut.addFavourite(testAssetId1)

        // Then
        val res = dataStore.data.first()[KeyFavourites]?.contains(testAssetId1)
        assertEquals(true, res)
    }

    @Test
    fun `given data store contains assetId as favourite when removeFavourite called with assetId then assetId removed as favourite`() = runTest {
        // Given
        // Prepopulate datastore to have assetId as favourite
        val (localCut, dataStore) = buildCutWithDatastoreBackedWithTempFile(backgroundScope)
        val favouritesSet = setOf(testAssetId1)
        dataStore.edit { it[KeyFavourites] = favouritesSet }

        // When
        localCut.removeFavourite(testAssetId1)

        // Then
        val result = dataStore.data.first()[KeyFavourites]?.contains(testAssetId1)
        assertEquals(false, result)
    }

    @Test
    fun `given favourites have been added to datastore when getAllFavourites called then set of favourite assetId's returned`() = runTest {
        // Given
        // Prepopulate datastore to have assetId's as favourite
        val (localCut, dataStore) = buildCutWithDatastoreBackedWithTempFile(backgroundScope)
        val favouritesSet = setOf(testAssetId1, testAssetId2)
        dataStore.edit { it[KeyFavourites] = favouritesSet }

        // When
        val result = localCut.getAllFavourites().first()

        // Then
        val expected = setOf(testAssetId1, testAssetId2)
        assertEquals(expected, result)
    }

    private fun buildCutWithDatastoreBackedWithTempFile(backgroundScope: CoroutineScope): Pair<FavoritesRepository, DataStore<Preferences>> {
        val dataStore = PreferenceDataStoreFactory.create(
            scope = backgroundScope,
            produceFile = { temporaryFolder.newFile(FavoritesPreferencesName) },
        )
        return FavoritesRepositoryImpl(dataStore) to dataStore
    }

    private fun mockPreferences1(): Preferences {
        val mockPreference = mockk<Preferences>()
        every { mockPreference[KeyFavourites] } returns setOf(testAssetId1, testAssetId2)
        return mockPreference
    }

    private fun mockPreferences2(): Preferences {
        val mockPreference = mockk<Preferences>()
        every { mockPreference[KeyFavourites] } returns setOf(testAssetId2)
        return mockPreference
    }

    private fun mockPreferences3(): Preferences {
        val mockPreference = mockk<Preferences>()
        every { mockPreference[KeyFavourites] } returns emptySet()
        return mockPreference
    }

    private fun mockPreferences4(): Preferences {
        val mockPreference = mockk<Preferences>()
        every { mockPreference[KeyFavourites] } returns setOf(testAssetId1, testAssetId2)
        return mockPreference
    }

    companion object {
        private const val FavoritesPreferencesName = "favorites_prefs_temp.preferences_pb"
        private val KeyFavourites = stringSetPreferencesKey("favourites")
        private val testAssetId1 = "testId1"
        private val testAssetId2 = "testId2"
    }
}