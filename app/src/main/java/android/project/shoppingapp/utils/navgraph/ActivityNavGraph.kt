package android.project.shoppingapp.utils.navgraph

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.project.shoppingapp.MainActivity
import android.project.shoppingapp.ui.authentication.AuthorizationActivity

object ActivityNavGraph {

    fun startApplicationFlow(activity: Activity, context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        activity.finish()
    }

    fun startLoginAndRegisterActivity(context: Context) {
        val intent = Intent(context, AuthorizationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}