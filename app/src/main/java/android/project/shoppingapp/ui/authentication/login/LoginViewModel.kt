package android.project.shoppingapp.ui.authentication.login

import android.project.shoppingapp.data.local.DataStoreManager
import android.project.shoppingapp.data.repository.firebase.AuthRepository
import android.project.shoppingapp.utils.Resources
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resources<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resources<FirebaseUser>?> = _loginFlow


    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val emailError = MutableStateFlow("")
    val passwordError = MutableStateFlow("")



    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    val isFormValid: Flow<Boolean> = combine(_email, _password) { email, password ->
        val isEmailCorrect = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordCorrect = password.length > 8

        emailError.value = if (!isEmailCorrect) {
            "Invalid email address"
        } else {
            ""
        }
        passwordError.value = if (!isPasswordCorrect) {
            "Passwotd length must be greater than 8"
        } else {
            ""
        }

        return@combine isEmailCorrect and isPasswordCorrect
    }

    fun loginUser() = viewModelScope.launch {
        _loginFlow.value = Resources.Loading(true)
        val result = repository.login(_email.value, _password.value)
        _loginFlow.value = result
    }

    fun setUserAuthenticated() = viewModelScope.launch {
        Log.d("SETUSERAUTH", "LOGIN")
        dataStoreManager.updateUserAuthentication(true)

    }



}


