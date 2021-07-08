package vn.teko.android.sample.payment.vnpay_gateway

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import vn.teko.android.payment.ui.PaymentActivity
import vn.teko.android.payment.ui.data.request.PaymentMainMethodRequest
import vn.teko.android.payment.ui.data.request.PaymentUIRequestBuilder
import vn.teko.android.payment.ui.data.request.VnPayGatewayRoute
import vn.teko.android.payment.ui.data.result.PaymentResult
import vn.teko.android.payment.ui.util.extension.payWith
import vn.teko.android.payment.v2.IPaymentGateway
import vn.teko.android.sample.payment.PaymentSampleApplication
import vn.teko.android.sample.payment.R
import vn.teko.android.sample.payment.showMessageDialog

/**
 * This class is example for pay directly through methods of VNPAY-GATEWAY
 *
 * @Note: VNPAY-GATEWAY only includes:
 * [PaymentMainMethodRequest.VNPayGatewayQR],
 * [PaymentMainMethodRequest.ATMBank],
 * [PaymentMainMethodRequest.CreditCard],
 * [PaymentMainMethodRequest.MobileBanking]
 */
class PayViaVnPayGatewayFragment : Fragment() {

    private val paymentGateway: IPaymentGateway
        get() = PaymentSampleApplication.shared.paymentGateway

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay_vnpay_gateway, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnPayVnPayQR).setOnClickListener {
            payDirectWithVNPayQR()
        }

        view.findViewById<Button>(R.id.btnPayATM).setOnClickListener {
            payDirectWithATM()
        }

        view.findViewById<Button>(R.id.btnPayCredit).setOnClickListener {
            payDirectWithCredit()
        }

        view.findViewById<Button>(R.id.btnPayMobileBanking).setOnClickListener {
            payDirectWithMobileBanking()
        }

        view.findViewById<Button>(R.id.btnPayATMWithBankCode).setOnClickListener {
            payDirectWithATMWithCode()
        }
    }

    /**
     * Pay directly with a method of VNPAY-GATEWAY: [PaymentMainMethodRequest.VNPayGatewayQR]
     *
     * Set order info to payment including [orderCode], [amount], ...
     * Set type specific for method online and passing params based on method selected
     * Refer to [PaymentMainMethodRequest] to see all available methods
     */
    private fun payDirectWithVNPayQR() {
        try {
            val builder = PaymentUIRequestBuilder()
                .setOrderCode(orderCode = "YourOrderCode")
                .setOrderAmount(amount = 10000)
                .setMainMethod(PaymentMainMethodRequest.VNPayGatewayQR(amount = 10000))
            paymentGateway.payWith(this, builder.build())
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * Pay directly with a method of VNPAY-GATEWAY: [PaymentMainMethodRequest.ATMBank]
     * It will open bank list screen
     *
     * Set order info to payment including [orderCode], [amount], ...
     * Set type specific for method online and passing params based on method selected
     * Refer to [PaymentMainMethodRequest] to see all available methods
     */
    private fun payDirectWithATM() {
        try {
            val builder = PaymentUIRequestBuilder()
                .setOrderCode(orderCode = "YourOrderCode")
                .setOrderAmount(amount = 10000)
                .setMainMethod(PaymentMainMethodRequest.ATMBank(amount = 10000))
            paymentGateway.payWith(this, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Pay directly with a method of VNPAY-GATEWAY: [PaymentMainMethodRequest.CreditCard]
     * It will open list of credit card screen
     *
     * Set order info to payment including [orderCode], [amount], ...
     * Set type specific for method online and passing params based on method selected
     * Refer to [PaymentMainMethodRequest] to see all available methods
     */
    private fun payDirectWithCredit() {
        try {
            val builder = PaymentUIRequestBuilder()
                .setOrderCode(orderCode = "YourOrderCode")
                .setOrderAmount(amount = 10000)
                .setMainMethod(PaymentMainMethodRequest.CreditCard(amount = 10000))
            paymentGateway.payWith(this, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Pay directly with a method of VNPAY-GATEWAY: [PaymentMainMethodRequest.MobileBanking]
     * It will open list of mobile banking app screen
     *
     * Set order info to payment including [orderCode], [amount], ...
     * Set type specific for method online and passing params based on method selected
     * Refer to [PaymentMainMethodRequest] to see all available methods
     */
    private fun payDirectWithMobileBanking() {
        try {
            val builder = PaymentUIRequestBuilder()
                .setOrderCode(orderCode = "YourOrderCode")
                .setOrderAmount(amount = 10000)
                .setMainMethod(PaymentMainMethodRequest.MobileBanking(amount = 10000))
            paymentGateway.payWith(this, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Pay directly with a method of VNPAY-GATEWAY: [PaymentMainMethodRequest.ATMBank]
     * It will pay directly with specific type of ATM
     *
     * Set order info to payment including [orderCode], [amount], ...
     * Set type specific for method online and passing params based on method selected
     * Refer to [PaymentMainMethodRequest] to see all available methods
     */
    private fun payDirectWithATMWithCode() {
        try {
            val builder = PaymentUIRequestBuilder()
                .setOrderCode(orderCode = "YourOrderCode")
                .setOrderAmount(amount = 10000)
                .setMainMethod(PaymentMainMethodRequest.ATMBank(amount = 10000, route = VnPayGatewayRoute.Bank("ncb")))
            paymentGateway.payWith(this, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Handle payment result received from PaymentSDK
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PaymentActivity.RC_PAYMENT -> {
                when (resultCode) {
                    PaymentActivity.RESULT_CANCELED -> {
                        Toast.makeText(requireContext(), "Payment is canceled", Toast.LENGTH_SHORT)
                            .show()
                    }
                    PaymentActivity.RESULT_FAILED -> {
                        val result =
                            data?.getParcelableExtra<PaymentResult>(PaymentActivity.PAYMENT_RESULT_KEY)
                        val extraMessage = result
                            ?.transactions
                            ?.map { "\n${it.methodCode} - ${it.amount} - isSuccess(${it.isSuccess}) - error: (${it.error?.code} - ${it.error?.message})\n" }
                            ?.toString()

                        requireContext().showMessageDialog(
                            "Thông báo",
                            "Thanh toán thất bại Order Code ${result?.order?.orderCode} \n " +
                                    "Amount: ${result?.order?.amount}.\n " +
                                    "Error: ${result?.error}.\n" +
                                    "$extraMessage",
                            R.string.Ok,
                            { _, _ -> })
                    }
                    PaymentActivity.RESULT_SUCCEEDED -> {
                        // More information of payment result
                        val result = data?.getParcelableExtra<PaymentResult>(
                            PaymentActivity.PAYMENT_RESULT_KEY
                        )
                        requireContext().showMessageDialog(
                            "Thông báo",
                            "Thanh toán thành công",
                            R.string.Ok,
                            { _, _ -> })
                    }
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
}