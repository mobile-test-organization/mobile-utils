package br.com.spotsales.views.extensions

import android.view.View
import android.view.WindowManager
import com.afollestad.materialdialogs.MaterialDialog

inline fun MaterialDialog.immersiveShow(func: MaterialDialog.() -> Unit): MaterialDialog {
    this.func()

    window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

    this.show()

    window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
    window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

    return this
}