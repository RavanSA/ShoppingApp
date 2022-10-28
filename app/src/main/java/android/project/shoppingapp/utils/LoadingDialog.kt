package android.project.shoppingapp.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.project.shoppingapp.R
import android.view.Window

class LoadingDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_progress_bar)
        setCancelable(false)
    }
}