package com.example.androidsample.di

import com.example.androidsample.repository.DetailsRepository
import com.example.androidsample.repository.DetailsRepositoryImpl
import com.example.androidsample.repository.ListRepository
import com.example.androidsample.repository.ListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @ViewModelScoped
    @Binds
    fun bindListRepository(listRepositoryImpl: ListRepositoryImpl): ListRepository
}

/**
 * Currently DetailsRepository is only used in ViewModels.
 * DetailsViewModel is not injected using @HiltViewModel so can't install in ViewModelComponent.
 */
@Module
@InstallIn(FragmentComponent::class)
interface DetailsRepositoryModule {

    @FragmentScoped
    @Binds
    fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository
}
