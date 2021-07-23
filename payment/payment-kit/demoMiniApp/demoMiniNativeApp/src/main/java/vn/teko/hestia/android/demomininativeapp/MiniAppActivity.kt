package vn.teko.hestia.android.demomininativeapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.mini_app_activity_mini_app.*
import vn.teko.android.payment.kit.PaymentKit
import vn.teko.android.payment.kit.PaymentResultV2Callback
import vn.teko.android.payment.kit.model.request.PaymentRequestBuilder
import vn.teko.android.payment.kit.model.request.PaymentV2Request
import vn.teko.android.payment.kit.model.result.PaymentV2Result
import vn.teko.terra.core.android.terra.TerraApp

class MiniAppActivity : AppCompatActivity() {

    private val paymentCallbackV2: PaymentResultV2Callback by lazy {
        object : PaymentResultV2Callback {
            override fun onResult(result: PaymentV2Result) {
                val paymentResultStatus = when (result.resultCode) {
                    PaymentV2Result.RESULT_CANCELED -> "canceled"
                    PaymentV2Result.RESULT_FAILED -> "failed"
                    PaymentV2Result.RESULT_SUCCEEDED -> "succeeded"
                    else -> "processed with unknown status"
                }
                val message = "Payment was $paymentResultStatus"

                AlertDialog.Builder(this@MiniAppActivity)
                    .setTitle(message)
                    .setMessage(result.error?.message.orEmpty())
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        TerraApp.initializeApp(application, MINI_APP_APP_NAME)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.mini_app_activity_mini_app)

        title = "Mini Application - Self Payment Flow"

        findViewById<Button>(R.id.btnRequestPaymentWithDefaultTerra).setOnClickListener {
            requestPaymentWithDefaultTerra()
        }

        findViewById<Button>(R.id.btnRequestPaymentSelfInitializedTerra).setOnClickListener {
            requestPaymentWithSelfInitializedTerra()
        }
    }

    private fun requestPaymentWithDefaultTerra() {
        PaymentKit.payForOrder(
            null,
            paymentRequest = getPaymentRequest(),
            callback = paymentCallbackV2
        )
    }

    private fun requestPaymentWithSelfInitializedTerra() {
        PaymentKit.payForOrder(
            MINI_APP_APP_NAME,
            paymentRequest = getPaymentRequest(),
            callback = paymentCallbackV2
        )
    }

    private fun getPaymentRequest(): PaymentV2Request {
        return PaymentRequestBuilder(
            PaymentV2Request.Order(
                orderCode = "abc-xyz-6789",
                orderAmount = 11111,
                displayOrderCode = "terra-6789"
            )
        )
            .setMainMethod(
                PaymentV2Request.Payment.MainMethod(
                    PaymentV2Request.Payment.Method.All,
                    11111
                )
            ).build()
    }

    companion object {
        private const val MINI_APP_APP_NAME = "MINI-APP-DEMO"
    }
}
