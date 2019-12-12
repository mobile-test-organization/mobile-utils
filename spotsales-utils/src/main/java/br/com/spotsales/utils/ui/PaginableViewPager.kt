package br.com.spotsales.utils.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class PaginableViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    var pageEnable: Boolean = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean =
            if (pageEnable) {
                super.onTouchEvent(ev)
            } else {
                false
            }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean =
            if (pageEnable) {
                super.onInterceptTouchEvent(ev)
            } else {
                false
            }

}
