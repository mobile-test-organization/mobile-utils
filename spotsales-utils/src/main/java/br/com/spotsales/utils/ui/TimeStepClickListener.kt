package br.com.spotsales.utils.ui

import android.os.SystemClock
import android.view.View

class TimeStepClickListener(
        private var listener: (View?) -> Unit = {}
) : View.OnClickListener {

    companion object {
        private const val CLICK_OFFSET_MILLISECONDS = 1000
    }

    private var lastClickTime: Long = 0

    //region ~~~~ OnClickListener ~~~~
    override fun onClick(v: View?) {
        if (SystemClock.elapsedRealtime() - lastClickTime < CLICK_OFFSET_MILLISECONDS) {
            return
        }

        lastClickTime = SystemClock.elapsedRealtime()
        listener(v)
    }
    //endregion
}