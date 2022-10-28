package android.project.shoppingapp.data.di

import android.content.Context
import android.project.assignmentweek5.data.local.database.AppDatabase
import android.project.shoppingapp.data.remote.api.CategoriesAPI
import android.project.shoppingapp.data.remote.api.ProductsAPI
import android.project.shoppingapp.data.repository.categories.CategoriesRepository
import android.project.shoppingapp.data.repository.categories.impl.CategoriesRepositoryImpl
import android.project.shoppingapp.data.repository.products.ProductRepository
import android.project.shoppingapp.data.repository.products.impl.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object CategoriesModule {


    @Provides
    fun provideApiService(retrofit: Retrofit): CategoriesAPI {
        return retrofit.create(CategoriesAPI::class.java)
    }

    @Provides
    fun provideCategoriesRepository(
        apiService: CategoriesAPI,
        appDatabase: AppDatabase
    ): CategoriesRepository {
        return CategoriesRepositoryImpl(apiService, appDatabase)
    }

}