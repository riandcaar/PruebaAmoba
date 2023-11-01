package com.felipecoronado.pruebaamoba.domain.di

import com.felipecoronado.pruebaamoba.data.local.repository.LocalRepository
import com.felipecoronado.pruebaamoba.data.network.repository.NetworkRepository
import com.felipecoronado.pruebaamoba.domain.repositories.LocalRepositoryInterface
import com.felipecoronado.pruebaamoba.domain.repositories.NetworkRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindsNetworkRepository(networkRepository: NetworkRepository)
            : NetworkRepositoryInterface

    @Binds
    abstract fun bindsLocalRepository(localRepositoryImpl: LocalRepository)
            : LocalRepositoryInterface
}
