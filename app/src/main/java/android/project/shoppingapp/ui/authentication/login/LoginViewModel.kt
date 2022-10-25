package android.project.shoppingapp.ui.authentication.login

import android.project.shoppingapp.data.repository.AuthRepository
import android.project.shoppingapp.utils.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resources<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resources<FirebaseUser>?> = _loginFlow

    init {

    }

    private fun loginUser(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resources.Loading(true)
        val result = repository.login(email, password)
        _loginFlow.value = result
    }

}