package android.project.shoppingapp.data.di

import android.project.shoppingapp.data.remote.service.FirebaseService
import android.project.shoppingapp.data.repository.firebase.AuthRepository
import android.project.shoppingapp.data.repository.firebase.impl.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseServiceModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseService(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): FirebaseService = FirebaseService(firebaseAuth, firebaseFirestore)

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun providesAuthRepository(firebaseRepository: AuthRepositoryImpl): AuthRepository = firebaseRepository

}