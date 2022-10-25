package android.project.shoppingapp.ui.authentication.registration

import android.project.shoppingapp.data.repository.AuthRepository
import android.project.shoppingapp.utils.Resources
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signup = MutableStateFlow<Resources<FirebaseUser>?>(null)
    val signup: StateFlow<Resources<FirebaseUser>?> = _signup

    val username: StateFlow<String> = MutableStateFlow("")

    val email : StateFlow<String> = MutableStateFlow("")

    val password : MutableStateFlow<String> = MutableStateFlow("")

    val confirmPassword : StateFlow<String> = MutableStateFlow("")

    init {

    }

    private fun signupUser(name: String, email: String, password: String) = viewModelScope.launch {
        _signup.value = Resources.Loading(true)
        val isFormValid = registrationFormValidation()
        if(isFormValid == null) {
            val result = repository.register(name, email, password)
            _signup.value = result
        }
    }


    fun registrationFormValidation(): String? {
        fun passwordValidation() = password.value == "" || password.value.length < 6
        fun emailValidation() = Patterns.EMAIL_ADDRESS.matcher(email.value).matches().not()
        fun usernameValidation() = username.value == ""
        fun confirmPasswordValidation() = confirmPassword.value == password.value


        return when {
            passwordValidation() -> "Password length must be grater than 6"
            emailValidation() -> "Incorrect email address"
            usernameValidation() -> "Username can not be empty"
            confirmPasswordValidation() -> "Repeated password must be match with the password"
            else -> null
        }

    }



}