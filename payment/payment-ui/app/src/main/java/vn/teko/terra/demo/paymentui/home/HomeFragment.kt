package vn.teko.terra.demo.paymentui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import vn.teko.android.payment.ui.PaymentActivity
import vn.teko.android.payment.ui.data.request.ExtraOptions
import vn.teko.android.payment.ui.data.request.LoyaltyMethodRequest
import vn.teko.android.payment.ui.data.request.PaymentMainMethodRequest
import vn.teko.android.payment.ui.data.request.PaymentUIRequestBuilder
import vn.teko.android.payment.ui.data.result.PaymentResult
import vn.teko.android.payment.ui.util.extension.payWith
import vn.teko.android.payment.v2.IPaymentGateway
import vn.teko.terra.demo.paymentui.PaymentSampleApplication
import vn.teko.terra.demo.paymentui.R
import vn.teko.terra.demo.paymentui.showMessageDialog

class HomeFragment : Fragment() {

    private val paymentGateway: IPaymentGateway
        get() = PaymentSampleApplication.shared.paymentGateway

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnOpenListMethods).setOnClickListener {
            openAvailableMethodOption()
        }

        view.findViewById<Button>(R.id.btnOptionPayDirect).setOnClickListener {
            payDirectWithAMethodOnly()
        }

        view.findViewById<Button>(R.id.btnPayThroughVNPAYGATEWAY).setOnClickListener {
            findNavController().navigate(R.id.action_home_to_pay_with_vnpay_gateway)
        }

        view.findViewById<Button>(R.id.btnPayWithOnlineAndLoyalty).setOnClickListener {
            payDirectWithLoyaltyAndOnlineMethod()
        }
    }

    /**
     * Open screen list of methods
     *
     * Set order info to payment including orderCode
     * Set type [PaymentMainMethodRequest.All] for method online and passing [amount]
     */
    private fun openAvailableMethodOption() {
        try {
            val builder = PaymentUIRequestBuilder()
                .setOrderCode(orderCode = "YourOrderCode")
                .setOrderAmount(amount = 10000L)
                .setMainMethod(PaymentMainMethodRequest.All(amount = 10000L))
                .setOptions(
                    ExtraOptions(
                        shouldShowPaymentResultScreen = true
                    )
                )
            paymentGateway.payWith(this, builder.build())
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * Pay directly with a method
     *
     * Set order info to payment including [orderCode], [amount], ...
     * Set type specific for method online and passing params based on method selected
     * Refer to [PaymentMainMethodRequest] to see all available methods
     */
    private fun payDirectWithAMethodOnly() {
        try {
            val builder = PaymentUIRequestBuilder()
                .setOrderCode(orderCode = "YourOrderCode")
                .setOrderAmount(amount = 1000)
                .setMainMethod(PaymentMainMethodRequest.VNPayGatewayQR(amount = 1000))
            paymentGateway.payWith(this, builder.build())
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * Pay directly with an online method and loyalty
     *
     * Set order info to payment including [orderCode], [amount], ...
     * Set loyalty method and passing params
     * Set a online method and passing params
     * @Notify: Not all methods in [PaymentMainMethodRequest] is online method!
     */
    private fun payDirectWithLoyaltyAndOnlineMethod() {
        try {
            val builder = PaymentUIRequestBuilder()
                .setOrderCode(orderCode = "YourOrderCode")
                .setOrderAmount(amount = 1000)
                .setLoyaltyMethod(LoyaltyMethodRequest(points = 1000, amount = 1000))
                .setMainMethod(PaymentMainMethodRequest.VNPayGatewayQR(amount = 1000))

            paymentGateway.payWith(this, builder.build())
        } catch (e: Throwable) {
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