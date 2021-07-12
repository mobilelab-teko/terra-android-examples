package vn.teko.hestia.android.demomininativeapp02

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
                    .create()
                    .show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mini_app_activity_mini_app)

        title = "Mini Application - Host App Payment Flow"

        findViewById<Button>(R.id.btnRequestPayment).setOnClickListener {
            requestPayment()
        }
    }

    private fun requestPayment() {
        val paymentRequestBuilder =
            PaymentRequestBuilder(
                PaymentV2Request.Order(
                    orderCode = "abc-xyz-6789",
                    orderAmount = 12345,
                    displayOrderCode = "terra-6789"
                )
            )
                .setMainMethod(
                    PaymentV2Request.Payment.MainMethod(
                        PaymentV2Request.Payment.Method.All,
                        12345
                    )
                )

        PaymentKit.payForOrder(
            null,
            paymentRequest = paymentRequestBuilder.build(),
            callback = paymentCallbackV2
        )
    }

    companion object {
        const val EXTRA_KEY_ID_TOKEN = "id-token"
        const val EXTRA_KEY_DUMMY_STRING = "dummy-string"
    }
}