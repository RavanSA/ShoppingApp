package android.project.shoppingapp.data.repository.firebase.impl

import android.project.shoppingapp.data.local.DataStoreManager
import android.project.shoppingapp.data.model.User
import android.project.shoppingapp.data.repository.firebase.AuthRepository
import android.project.shoppingapp.utils.Resources
import android.project.shoppingapp.utils.firebase.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authFirebase: FirebaseAuth,
    private val dataStoreManager: DataStoreManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resources<FirebaseUser> {
        return try {
            Resources.Loading<FirebaseUser>(true)
            val result = authFirebase.signInWithEmailAndPassword(email, password).await()
            authFirebase.currentUser?.let { dataStoreManager.setUserId(it.uid) }
            Resources.Loading<FirebaseUser>(false)
            Resources.Success<FirebaseUser>(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resources.Error(e.message.toString())
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Resources<FirebaseUser> {
        return try {
            Resources.Loading<FirebaseUser>(true)
            val result = authFirebase.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )?.await()
            val data = HashMap<String, Any>()
            data["email"] = email
            data["username"] = name
            getUid()?.let {
                FirebaseFirestore.getInstance().collection("users")
                    .document(it).set(data)
            }
            authFirebase.currentUser?.let { dataStoreManager.setUserId(it.uid) }
            Resources.Loading<FirebaseUser>(false)
            return Resources.Success<FirebaseUser>(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resources.Error(e.message.toString())
        }
    }

    override suspend fun logout() {
        authFirebase.currentUser?.let { dataStoreManager.setUserId(null) }
        authFirebase.signOut()
    }

    override suspend fun getUid(): String? = authFirebase.uid


    override suspend fun getUserProfileInfo(): Flow<User?> = channelFlow {
        var user: User? = null
        val callBack =
            FirebaseFirestore.getInstance().collection("users")
                .document(authFirebase.currentUser?.uid.toString())
                .get().addOnSuccessListener {

                    user = it.toObject(User::class.java)
                    trySendBlocking(user)
                }.addOnFailureListener {
                    trySendBlocking(null)
                }
        awaitClose { callBack.isCanceled() }
    }


}