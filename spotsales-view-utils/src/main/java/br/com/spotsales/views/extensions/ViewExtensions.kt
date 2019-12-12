package br.com.spotsales.views.extensions

import android.view.View
import br.com.spotsales.views.utils.TimeStepClickListener

fun View.setTimeClickListener(l: (View?) -> Unit) = this.setOnClickListener(TimeStepClickListener(l))
