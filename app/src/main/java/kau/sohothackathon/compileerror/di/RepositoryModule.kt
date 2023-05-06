package kau.sohothackathon.compileerror.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kau.sohothackathon.compileerror.data.AddressRepositoryImpl
import kau.sohothackathon.compileerror.domain.AddressRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsAddressRepository(
        addressRepositoryImpl: AddressRepositoryImpl
    ): AddressRepository

}