package com.jarc.marvelcharacterswiki.ui.utils

import android.app.AlertDialog
import android.content.Context

object ViewUtils {

    fun onDialog(msg: String, context: Context, onRetry: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
            .setPositiveButton("Yes") { _, _ ->
                onRetry()
            }
            .setNegativeButton("No", null)
            .setCancelable(true)
        val dialog = builder.create()
        dialog.show()
    }
}