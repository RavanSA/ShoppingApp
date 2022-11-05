package android.project.shoppingapp.data.di

import android.project.assignmentweek5.data.local.database.AppDatabase
import android.project.shoppingapp.data.repository.cartrepository.CartRepository
import android.project.shoppingapp.data.repository.cartrepository.impl.CartRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class CartModule {


    @Provides
    fun provideCartRepository(
        appDatabase: AppDatabase
    ) : CartRepository {
        return CartRepositoryImpl(appDatabase)
    }
}