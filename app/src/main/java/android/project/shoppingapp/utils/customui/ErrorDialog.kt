package android.project.shoppingapp.utils.customui

import android.app.Dialog
import android.content.Context
import android.project.shoppingapp.R
import android.project.shoppingapp.databinding.CustomErrorDialogBinding
import android.project.shoppingapp.utils.Constants
import android.view.LayoutInflater
import android.view.Window
import androidx.core.content.ContextCompat


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