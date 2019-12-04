package br.com.spotsales.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu

fun PopupMenu.itemVisibility(context: AppCompatActivity, itemId: Int, isVisible: Boolean) =
    menu.findItem(itemId).also { item ->
        item.isVisible = isVisible
        context.invalidateOptionsMenu()
    }
