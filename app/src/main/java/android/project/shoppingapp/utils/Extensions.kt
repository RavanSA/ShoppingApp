package android.project.shoppingapp.utils

import android.project.shoppingapp.utils.customui.showCustomDialog
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingComponent


infix fun <T> Resources<T>.takeIfSuccess(onSuccess: Resources.Success<T>.() -> Unit): Resources<T> {
    return when (this) {
        is Resources.Success -> {
            onSuccess(this)
            this
        }
        else -> {
            this
        }
    }
}

infix fun <T> Resources<T>.takeIfError(onError: Resources.Error<T>.() -> Unit): Resources<T> {
    return when (this) {
        is Resources.Error -> {
            onError(this)
            this
        }
        else -> {
            this
        }
    }
}

infix fun <T> Resources<T>.takeIfLoading(onLoading: Resources.Loading<T>.() -> Unit): Resources<T> {
    return when (this) {
        is Resources.Loading -> {
            onLoading(this)
            this
        }
        else -> {
            this
        }
    }
}


inline fun <T : DataBindingComponent> AppCompatActivity.dataBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

