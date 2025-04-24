package com.gibconsulting.guardianapisample.data.remote.di

import com.gibconsulting.guardianapisample.core.BuildInfo
import com.gibconsulting.guardianapisample.data.remote.client.GuardianClient
import com.gibconsulting.guardianapisample.data.remote.repository.ArticlesRepositoryImpl
import com.gibconsulting.guardianapisample.domain.repository.ArticlesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherIO

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {
    @Binds
    internal abstract fun providesArticlesRepository(articlesRepositoryImpl: ArticlesRepositoryImpl): ArticlesRepository

    companion object {
        private const val BASE_URL = "https://content.guardianapis.com"
        private const val HEADER_API_KEY = "api-key"
        // The API key has been placed here for convenience only. It should be stored in local.properties and
        // separately on the CI build machine.
        // This is a test key and can expire.
        // To check if it is still valid goto https://content.guardianapis.com/search?api-key=c4e32b46-0ef0-41ec-93fb-51b9d59170b0
        // If it shows as unauthorised get a new key.
        // Go to https://bonobo.capi.gutools.co.uk/register/developer and register as a developer.
        // Then replace the key below with the one from the site.
        private const val API_KEY_VALUE = "c4e32b46-0ef0-41ec-93fb-51b9d59170b0"

        @Provides
        @DispatcherIO
        fun providesDispatcherIO() = Dispatchers.IO

        @Provides
        @Singleton
        internal fun okHttpCallFactory(buildInfo: BuildInfo): Call.Factory {
            val builder = OkHttpClient.Builder()
                .addInterceptor(getAuthInterceptor())
            if (buildInfo.isDebug) {
                builder.addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                )
            }
            return builder.build()
        }

        private fun getAuthInterceptor(): Interceptor {
            return Interceptor { chain ->
                val original = chain.request()
                val hb = original.headers.newBuilder()
                hb.add(HEADER_API_KEY, API_KEY_VALUE)
                chain.proceed(original.newBuilder().headers(hb.build()).build())
            }
        }

        @Provides
        @Singleton
        internal fun provideGuardianClient(
            okhttpCallFactory: dagger.Lazy<Call.Factory>,
        ): GuardianClient {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                // We use callFactory lambda here with dagger.Lazy<Call.Factory>
                // to prevent initializing OkHttp on the main thread.
                .callFactory { okhttpCallFactory.get().newCall(it) }
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(GuardianClient::class.java)
        }
    }
}