package com.gibconsulting.guardianapisample.domain.di

import com.gibconsulting.guardianapisample.domain.usecase.AddToFavoriteArticlesUseCase
import com.gibconsulting.guardianapisample.domain.usecase.GetArticleDetailsUseCase
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesGroupedUseCase
import com.gibconsulting.guardianapisample.domain.usecase.GetLatestArticlesUseCase
import com.gibconsulting.guardianapisample.domain.usecase.IsFavoriteArticleUseCase
import com.gibconsulting.guardianapisample.domain.usecase.RemoveFromFavoriteArticlesUseCase
import com.gibconsulting.guardianapisample.domain.usecase.impl.AddToFavoriteArticlesUseCaseImpl
import com.gibconsulting.guardianapisample.domain.usecase.impl.GetArticleDetailsUseCaseImpl
import com.gibconsulting.guardianapisample.domain.usecase.impl.GetLatestArticlesGroupedUseCaseImpl
import com.gibconsulting.guardianapisample.domain.usecase.impl.GetLatestArticlesUseCaseImpl
import com.gibconsulting.guardianapisample.domain.usecase.impl.IsFavoriteArticleUseCaseImpl
import com.gibconsulting.guardianapisample.domain.usecase.impl.RemoveFromFavoriteArticlesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    internal abstract fun providesAddToFavoriteArticlesUseCase(impl: AddToFavoriteArticlesUseCaseImpl): AddToFavoriteArticlesUseCase

    @Binds
    internal abstract fun providesGetArticleDetailsUseCase(impl: GetArticleDetailsUseCaseImpl): GetArticleDetailsUseCase

    @Binds
    internal abstract fun providesGetLatestArticlesGroupedUseCase(impl: GetLatestArticlesGroupedUseCaseImpl): GetLatestArticlesGroupedUseCase

    @Binds
    internal abstract fun providesGetLatestArticlesUseCase(impl: GetLatestArticlesUseCaseImpl): GetLatestArticlesUseCase

    @Binds
    internal abstract fun providesIsFavoriteArticleUseCase(impl: IsFavoriteArticleUseCaseImpl): IsFavoriteArticleUseCase

    @Binds
    internal abstract fun providesRemoveFromFavoriteArticlesUseCase(impl: RemoveFromFavoriteArticlesUseCaseImpl): RemoveFromFavoriteArticlesUseCase
}