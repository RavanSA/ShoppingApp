package android.project.shoppingapp.data.repository.firebase

import android.project.shoppingapp.data.model.User
import android.project.shoppingapp.utils.Resources
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Resources<FirebaseUser>
    suspend fun register(name: String, email: String, password: String): Resources<FirebaseUser>
    fun logout()
    suspend fun getUid(): String?
    suspend fun getUserProfileInfo(): Flow<User?>
}