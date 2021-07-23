package vn.teko.terra.demo.paymentui

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

internal fun Context?.showMessageDialog(
    title: String,
    message: String,
    positiveButtonTitle: Int,
    positiveButtonAction: (DialogInterface, Int) -> Unit,
    cancelable: Boolean = true
): AlertDialog? {
    return if (this != null) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonTitle, positiveButtonAction)
            .setCancelable(cancelable)
            .show()
    } else null
}