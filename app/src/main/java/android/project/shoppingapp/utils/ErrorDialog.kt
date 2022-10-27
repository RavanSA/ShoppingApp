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

//class ErrorDialog(
//    context: Context
//    ) : DialogFragment() {
//
//    private lateinit var binding: CustomErrorDialogBinding
//
//
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState)
//        binding = CustomErrorDialogBinding.inflate(LayoutInflater.from(context))
//
//        return AlertDialog.Builder(requireActivity())
//            .setView(binding.root)
//            .create()
//
//    }
//
////    fun errorDialogShow(msg: String, status: String) {
//////         binding = DataBindingUtil.inflate(
//////             LayoutInflater.from(context),
//////             R.layout.custom_error_dialog, null, false);
//////         setContentView(binding.root)
//////         val messageBoxView = LayoutInflater.from(context).inflate(R.layout.custom_error_dialog, null)
//////
//////         //AlertDialogBuilder
//////         val messageBoxBuilder = AlertDialog.Builder(context).setView(messageBoxView)
//////         messageBoxBuilder.context.
////         binding.tvErrorDialog.text = msg
////        if(status == Constants.SUCCES_DIALOG) {
////            binding.ivErrorDialog.setImageDrawable(
////                ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_circle_outline_24)
////            )
////            binding.ivErrorDialog.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
////        } else if (status == Constants.ERROR_DIALOG) {
////            binding.ivErrorDialog.setImageDrawable(
////                ContextCompat.getDrawable(context, R.drawable.ic_baseline_error_outline_24)
////            )
////            binding.ivErrorDialog.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed))
////        }
////    }
//
//}


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



