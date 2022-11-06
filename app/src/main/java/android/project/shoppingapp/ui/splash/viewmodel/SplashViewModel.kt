package android.project.shoppingapp.ui.splash.viewmodel

import android.project.shoppingapp.data.local.DataStoreManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _authEvent: MutableSharedFlow<SplashScreenEvent> = MutableSharedFlow()
    val authEvent: SharedFlow<SplashScreenEvent> = _authEvent

    init {
        isUserAuthenticated()
    }

    private fun isUserAuthenticated() = viewModelScope.launch {
        dataStoreManager.userAuthentication.collect { isAuthenticated ->
            if (isAuthenticated) {
                _authEvent.emit(SplashScreenEvent.RedirectToApplicationFlow)
            } else {
                dataStoreManager.firstTimeLogin.collect { isFirsTimeLogin ->
                    if (!isFirsTimeLogin) {
                        _authEvent.emit(SplashScreenEvent.RedirectToRegistrationFlow)
                    } else {
                        _authEvent.emit(SplashScreenEvent.RedirectToOnBoardingScreen)
                    }
                }
            }
        }
    }
}