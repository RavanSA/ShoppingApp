package android.project.shoppingapp.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.CustomErrorDialogBinding
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment


fun showCustomDialog(title: String, status: String, context: Context) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    val binding = CustomErrorDialogBinding
        .inflate(LayoutInflater.from(context))
    with(binding) {
        tvErrorDialog.text = title
        btnErrorDialog.setOnClickListener {
            dialog.dismiss()
        }
        if (status == Constants.ERROR_DIALOG) {
        ivErrorDialog.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_error_outline_24)
            )
        } else if (status == Constants.SUCCES_DIALOG) {
            ivErrorDialog.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_circle_outline_24)
            )
        }
    }
    dialog.setContentView(binding.root)
    dialog.show()

}



