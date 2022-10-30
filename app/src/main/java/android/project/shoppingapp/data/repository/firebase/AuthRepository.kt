package android.project.shoppingapp.data.repository.firebase

import android.project.shoppingapp.utils.Resources
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Resources<FirebaseUser>
    suspend fun register(name: String, email: String, password: String): Resources<FirebaseUser>
    fun logout()
}