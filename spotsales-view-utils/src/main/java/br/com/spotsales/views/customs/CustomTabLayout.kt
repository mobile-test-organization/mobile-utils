package br.com.spotsales.views.customs

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.tabs.TabLayout

class CustomTabLayout : TabLayout {

    private var normalTypeface: Typeface? = null
    private var selectedTypeface: Typeface? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun setNormalFont(path: String) {
        normalTypeface = Typeface.createFromAsset(context.assets, path)
    }

    fun setSelectedFont(path: String) {
        selectedTypeface = Typeface.createFromAsset(context.assets, path)
    }

    private fun init() {
        addOnTabSelectedListener()
    }

    private fun changeTabTypeFace(tabView: ViewGroup, typeface: Typeface?) {
        for (j in 0 until tabView.childCount) {
            if (tabView.getChildAt(j) is AppCompatTextView) {
                (tabView.getChildAt(j) as TextView).typeface = typeface
            }
        }
    }

    private fun addOnTabSelectedListener() {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: Tab?) {
            }

            override fun onTabUnselected(tab: Tab?) {
                tab?.view?.also { view ->
                    changeTabTypeFace(view, normalTypeface)
                }
            }

            override fun onTabSelected(tab: Tab?) {
                tab?.view?.also { view ->
                    changeTabTypeFace(view, selectedTypeface)
                }
            }
        })
    }
}