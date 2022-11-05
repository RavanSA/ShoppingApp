package android.project.shoppingapp.data.repository.firebase.impl

import android.annotation.SuppressLint
import android.project.shoppingapp.data.model.User
import android.project.shoppingapp.data.repository.firebase.AuthRepository
import android.project.shoppingapp.utils.Resources
import android.project.shoppingapp.utils.firebase.await
import android.util.Log
import androidx.sqlite.db.SupportSQLiteCompat.Api16Impl.cancel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
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
            Resources.Error(e.message.toString())
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Resources<FirebaseUser> {
        return try {
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
            return Resources.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resources.Error(e.message.toString())
        }
    }



    override fun logout() {
        authFirebase.signOut()
    }

    override suspend fun getUid(): String? = authFirebase.uid


     override suspend fun getUserProfileInfo():Flow<User?> = channelFlow {
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