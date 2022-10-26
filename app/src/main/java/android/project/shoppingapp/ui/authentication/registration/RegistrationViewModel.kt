package android.project.shoppingapp.ui.authentication.registration

import android.project.shoppingapp.data.repository.AuthRepository
import android.project.shoppingapp.utils.Resources
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signup = MutableStateFlow<Resources<FirebaseUser>?>(null)
    val signup: StateFlow<Resources<FirebaseUser>?> = _signup


    private val _username = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _confirmPassword = MutableStateFlow("")

    val userNameError = MutableStateFlow("")
    val emailError = MutableStateFlow("")
    val passwordError = MutableStateFlow("")
    val confirmPasswordError = MutableStateFlow("")


    fun setUserName(username: String) {
        _username.value = username
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    val isFormValid: Flow<Boolean> = combine(_username, _email,
        _password, _confirmPassword) {
            username, email, password, confirmPassword ->
        val regexString = "[a-zA-Z]+"
        val isUserNameCorrect = username.matches(regexString.toRegex())
        val isEmailCorrect = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordCorrect = password.length > 8
        val isConfirmPasswordCorrect = confirmPassword == password

        userNameError.value = if(!isUserNameCorrect){ "Username cannot empty"} else {""}
        emailError.value = if(!isEmailCorrect){ "Invalid email address"} else {""}
        passwordError.value = if(!isPasswordCorrect){ "Passwotd length must be greater than 8"} else {""}
        confirmPasswordError.value = if(!isConfirmPasswordCorrect){ "Confirm password must be equal to password"} else {""}

        return@combine isUserNameCorrect and isEmailCorrect and isPasswordCorrect and isConfirmPasswordCorrect
    }


     fun signupUser() = viewModelScope.launch {
        _signup.value = Resources.Loading(true)
            val result = repository.register(_username.value, _email.value, _password.value)
            _signup.value = result
     }

}