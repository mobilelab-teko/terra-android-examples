package vn.teko.terra.demo.paymentkit.utils.extensions

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import vn.teko.terra.demo.paymentkit.R

internal fun Context?.showProgressDialog(): AlertDialog? {
    return if (this != null) {
        AlertDialog.Builder(this, R.style.ProgressBarStyle)
            .setCancelable(false)
            .setView(R.layout.layout_dialog_progress)
            .show()
    } else {
        null
    }
}


internal fun Context?.showMessageDialog(
    title: String,
    message: String,
    positiveButtonTitle: Int,
    positiveButtonAction: (DialogInterface, Int) -> Unit,
    negativeButtonTitle: Int = R.string.cancel,
    negativeButtonAction: ((DialogInterface, Int) -> Unit)? = null,
    cancelable: Boolean = true
): AlertDialog? {
    return if (this != null) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonTitle, positiveButtonAction)
            .setNegativeButton(negativeButtonTitle, negativeButtonAction)
            .setCancelable(cancelable)
            .show()
    } else null
}