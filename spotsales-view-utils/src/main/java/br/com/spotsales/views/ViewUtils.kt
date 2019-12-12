package br.com.spotsales.views

import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager

object ViewUtils {

    fun setVisibility(visibility: Int, vararg views: View) {
        for (v in views) {
            v.visibility = visibility
        }
    }

    fun hideKeyboard(context: Context, windowToken: IBinder) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}
