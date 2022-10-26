package android.project.shoppingapp.data.remote.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val firebaseAuthentication: FirebaseAuth,
    private val firebaseFireStore: FirebaseFirestore
) {

    fun saveUserInformation() {

    }

}