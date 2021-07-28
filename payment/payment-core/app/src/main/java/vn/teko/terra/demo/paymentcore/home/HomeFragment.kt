package vn.teko.terra.demo.paymentcore.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.teko.android.core.util.Result
import vn.teko.android.core.util.android.extension.showToast
import vn.teko.android.payment.manager.TerraPayment
import vn.teko.android.payment.v2.model.exposing.builder.*
import vn.teko.android.payment.v2.model.exposing.builder.order.OrderItemBuilder
import vn.teko.android.payment.v2.model.exposing.payment.methods.qr.QRMMSRequest
import vn.teko.android.payment.v2.model.exposing.result.*
import vn.teko.android.payment.v2.model.exposing.transaction.TransactionStatus
import vn.teko.android.payment.v2.util.extensions.errorDetails
import vn.teko.terra.core.android.terra.TerraApp
import vn.teko.terra.demo.paymentcore.R

/**
 * A simple [Fragment] subclass.
 * This fragment includes basic APIs to create flow payment
 */
class HomeFragment : Fragment() {

    private val paymentGateway by lazy {
        TerraPayment.getInstance(
            terraApp = TerraApp.getInstance()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnGetAllPaymentMethods.setOnClickListener {
            lifecycleScope.launch { getAllPaymentMethod() }
        }

        btnInitTransaction.setOnClickListener {
            lifecycleScope.launch { initTransaction() }
        }

        btnGetTransactions.setOnClickListener {
            lifecycleScope.launch { getTransactions() }
        }

        btnNavToOtherApi.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_other_apis)
        }
    }

    private fun showPopup(title: String, message: String) {
        AlertDialog.Builder(requireContext()).setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    /**
     * This function is example for get all methods that your merchant is supported
     *
     * @return list of payment method
     */
    private suspend fun getAllPaymentMethod(): Result<PaymentMethodsResult, Throwable> {
        val request = PaymentMethodsBuilder()
            .setAmount(10000L)
            .setOrderItems(
                listOf(
                    OrderItemBuilder()
                        .setSKU("SKU123456")
                        .setPrice(10000L)
                        .setQuantity(1)
                )
            )

        return paymentGateway.getPaymentMethods(request).also { result ->
            if (result.isSuccess() && result.getOrNull() != null) {
                showToast("Get Payment Methods success")
                val methods = result.get().methods
                showPopup(
                    title = "Available Methods",
                    message = "You can init transaction with methods: " +
                            "${methods.joinToString(transform = {it.displayName})}"
                )
                Timber.i("Available methods: ${methods.joinToString()}")
            } else {
                showToast("Get Payment Methods failed")
                Timber.e(result.exceptionOrNull())
            }
        }
    }

    /**
     * This function is example for init transaction
     *
     * @return result of init transaction
     */
    private suspend fun initTransaction(): Result<InitPaymentAIOResult, Throwable> {
        val request = PayAIOBuilder()
            .setOrderCode("OrderCode-123")
            .setTotalPaymentAmount(10000L)
            .setPaymentMethods(
                listOf(QRMMSRequest(amount = 10000L))
            )
            .setNotes("This is a note.")

        return paymentGateway.initAIOTransaction(request).also { result ->
            if (result.isSuccess() && result.getOrNull() != null) {
                showToast("Init Transaction success")
                val requestId = result.get().requestId
                // Observe transaction result
                observeTransactionResult(requestId)

                showPopup(
                    title = "Init Transaction Success",
                    message = "OderCode: OrderCode-123 \n" +
                            "Total payment: 10000 VND \n" +
                            "Method payment: VNPAY-QRCODE."
                )
                Timber.i("Init transaction success with requestId: ${result.get().requestId}")
            } else {
                showToast("Init Transaction failed")
                Timber.e(result.exceptionOrNull())
            }
        }
    }

    /**
     * This function is example to get transactions information.
     *
     * @return a list of [TransactionsResult.Transaction]
     */
    private suspend fun getTransactions(): Result<TransactionsResult, Throwable> {
        val requestBuilder = TransactionsRequestBuilder()
            .setFilterByStatuses(arrayListOf(TransactionStatus.Success))

        return paymentGateway.getTransactions(requestBuilder).also { result ->
            if (result.isSuccess() && result.getOrNull() != null) {
                showToast("Get list of transactions success")
                Timber.i("GetTransaction success, message: ${result.get().message}")
            } else {
                showToast("Get list of transactions fail")
                Timber.e(result.exceptionOrNull())
            }
        }
    }

    /**
     * This function will observer result of specific transaction with [requestID] which will be
     * received in result of init transaction [initTransaction].
     *
     * @param requestID: used to distinguish transactions
     */
    private fun observeTransactionResult(requestID: String) {
        paymentGateway.observePaymentResult(requestID)
            .onEach { result ->
                if (result.isSuccess()) {
                    showPopupTransactionResult(true, requestID)
                } else {
                    val errorDetail = result.errorDetails()
                    showPopupTransactionResult(false, requestID)
                }
                Timber.i("$result")
            }.launchIn(lifecycleScope)
    }

    private fun showPopupTransactionResult(success: Boolean, requestId: String) {
        val result = if (success) "success" else "fail"
        AlertDialog.Builder(requireContext()).setTitle("Transaction Result")
            .setMessage("The transaction with requestId: $requestId is $result")
            .setPositiveButton("OK") { _, _ ->
                //TODO: handle process when receive transaction result
            }
            .show()
    }
}