package android.project.shoppingapp.data.di

import android.project.assignmentweek5.data.local.database.AppDatabase
import android.project.shoppingapp.data.remote.api.ProductsAPI
import android.project.shoppingapp.data.repository.products.ProductRepository
import android.project.shoppingapp.data.repository.products.impl.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
class ProductsModule {


    @Provides
    fun provideApiService(retrofit: Retrofit): ProductsAPI {
        return retrofit.create(ProductsAPI::class.java)
    }

    @Provides
    fun provideProductRepository(
        apiService: ProductsAPI,
        appDatabase: AppDatabase
    ): ProductRepository {
        return ProductRepositoryImpl(apiService, appDatabase)
    }

}

