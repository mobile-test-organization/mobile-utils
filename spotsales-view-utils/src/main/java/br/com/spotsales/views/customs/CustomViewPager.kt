package br.com.spotsales.views.customs

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var enabledScroll: Boolean = false

    override fun onTouchEvent(event: MotionEvent): Boolean =
            if (enabledScroll) {
                super.onTouchEvent(event)
            } else {
                false
            }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean =
            if (enabledScroll) {
                super.onInterceptTouchEvent(event)
            } else {
                false
            }
}