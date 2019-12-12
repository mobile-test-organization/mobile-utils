package br.com.spotsales.views.extensions

import android.view.Menu

fun Menu.itemVisibility(itemId: Int, isVisible: Boolean) =
    this.findItem(itemId).also { item ->
        item.isVisible = isVisible
    }
