package br.com.spotsales.utils.extensions

import android.view.View
import br.com.spotsales.utils.ui.TimeStepClickListener

fun View.setTimeClickListener(l: (View?) -> Unit) = this.setOnClickListener(TimeStepClickListener(l))
