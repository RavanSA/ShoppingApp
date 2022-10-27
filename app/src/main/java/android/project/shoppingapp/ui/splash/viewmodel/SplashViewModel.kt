package android.project.shoppingapp.ui.splash.viewmodel

import android.project.shoppingapp.data.local.DataStoreManager
import android.project.shoppingapp.ui.splash.viewmodel.SplashScreenEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    private val _authEvent: MutableStateFlow<SplashScreenEvent>
        get() = MutableStateFlow(SplashScreenEvent.RedirectToRegistrationFlow)
    val authEvent = _authEvent.asStateFlow()


    init {
        isUserAuthenticated()
    }

    private fun isUserAuthenticated() = viewModelScope.launch {
        dataStoreManager.userAuthentication.collect { isAuthenticated ->
            if (isAuthenticated) {
                _authEvent.value = SplashScreenEvent.RedirectToApplicationFlow
            } else {
                _authEvent.value = SplashScreenEvent.RedirectToRegistrationFlow
            }
        }
    }

}

