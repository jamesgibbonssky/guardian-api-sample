package com.gibconsulting.guardianapisample.di

import com.gibconsulting.guardianapisample.BuildConfig
import com.gibconsulting.guardianapisample.core.BuildInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    companion object {
        @Provides
        @Singleton
        fun providesBuildInfo(): BuildInfo {
            return object : BuildInfo {
                override val isDebug: Boolean
                    get() = BuildConfig.DEBUG
            }
        }
    }
}