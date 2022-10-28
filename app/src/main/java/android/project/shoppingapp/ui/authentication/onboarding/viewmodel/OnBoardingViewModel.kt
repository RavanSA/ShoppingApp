package android.project.shoppingapp.ui.authentication.onboarding.viewmodel

import android.project.shoppingapp.data.local.DataStoreManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    fun userPassedOnBoardScreens() = viewModelScope.launch{
        dataStoreManager.updateFirstTimeLogin(false)
    }

}