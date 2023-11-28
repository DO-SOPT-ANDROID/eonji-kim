package org.sopt.dosopttemplate.util

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.makeToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}