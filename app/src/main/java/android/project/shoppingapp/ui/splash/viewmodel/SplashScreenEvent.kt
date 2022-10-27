package android.project.shoppingapp.ui.splash.viewmodel

sealed class SplashScreenEvent {
    object RedirectToRegistrationFlow : SplashScreenEvent()
    object RedirectToApplicationFlow : SplashScreenEvent()
}