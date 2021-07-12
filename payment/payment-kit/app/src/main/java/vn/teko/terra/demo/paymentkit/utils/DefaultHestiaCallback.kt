package vn.teko.terra.demo.paymentkit.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import vn.teko.terra.demo.paymentkit.utils.extensions.showMessageDialog
import vn.teko.terra.demo.paymentkit.utils.extensions.showProgressDialog
import vn.teko.hestia.core.HestiaCallback
import vn.teko.hestia.core.util.HestiaError
import vn.teko.hestia.core.util.HestiaLoadingState
import vn.teko.terra.demo.paymentkit.R

class DefaultHestiaCallback(private val context: Context) : HestiaCallback {

    private var progressDialog: AlertDialog? = null

    override fun onSuccess() {
    }

    override fun onError(error: HestiaError) {
        context.showMessageDialog(
            title = "Starting app error",
            message = error.message.orEmpty(),
            positiveButtonTitle = R.string.ok,
            positiveButtonAction = { _, _ -> },
            cancelable = true
        )
    }

    override fun onLoadingStateChanged(state: HestiaLoadingState) {
        when (state) {
            HestiaLoadingState.Start -> showLoading()
            else -> hideLoading()
        }
    }

    private fun showLoading() {
        progressDialog = context.showProgressDialog()
    }

    private fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

}