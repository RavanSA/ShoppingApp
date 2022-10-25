package android.project.shoppingapp.data.repository.impl

import android.project.shoppingapp.data.repository.AuthRepository
import android.project.shoppingapp.utils.Resources
import android.project.shoppingapp.utils.firebase.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authFirebase: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resources<FirebaseUser> {
        return try {
            val result = authFirebase.signInWithEmailAndPassword(email, password).await()
            Resources.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resources.Error(e.toString())
        }
    }

    override suspend fun register(name: String, email: String, password: String): Resources<FirebaseUser> {
        return try {
            val result = authFirebase.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            return Resources.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resources.Error(e.toString())
        }
    }

    override fun logout() {
        authFirebase.signOut()
    }

}