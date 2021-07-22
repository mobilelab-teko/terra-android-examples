package vn.teko.terra.demo.paymentcore

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.teko.android.core.util.Result
import vn.teko.android.payment.v2.model.api.response.AvailableBank
import vn.teko.android.payment.v2.model.exposing.builder.*
import vn.teko.android.payment.v2.model.exposing.builder.order.OrderItemBuilder
import vn.teko.android.payment.v2.model.exposing.payment.methods.card.SPosCardMethodRequest
import vn.teko.android.payment.v2.model.exposing.payment.methods.loyalty.LoyaltyMethodRequest
import vn.teko.android.payment.v2.model.exposing.payment.methods.qr.QRMMSRequest
import vn.teko.android.payment.v2.model.exposing.result.*
import vn.teko.android.payment.v2.model.exposing.result.payment.method.PaymentMethod
import vn.teko.android.payment.v2.model.exposing.transaction.TransactionStatus
import vn.teko.android.payment.v2.util.extensions.errorDetails

class MainActivity : AppCompatActivity() {

    private val paymentGateway by lazy {
        (application as ExampleApp).paymentGateway
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeTransactionInit()
        observeAction()
    }

    private fun observeAction() {
        btnRegisterMMC.setOnClickListener {
            lifecycleScope.launch { registerMMC() }
        }

        btnGetAllPaymentMethods.setOnClickListener {
            lifecycleScope.launch { getAllPaymentMethod() }
        }

        btnGetAllBanksSupported.setOnClickListener {
            lifecycleScope.launch { getBanksSupported() }
        }

        btnGetInstallmentPartners.setOnClickListener {
            lifecycleScope.launch { getInstallmentPartners() }
        }

        btnInitTransaction.setOnClickListener {
            lifecycleScope.launch { initTransaction() }
        }

        btnGetTransactions.setOnClickListener {
            lifecycleScope.launch { getTransactions() }
        }
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

        return paymentGateway.getPaymentMethods(request)
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
                listOf(
                    QRMMSRequest(amount = 9000L),
                    LoyaltyMethodRequest(
                        amount = 1000L,
                        points = 500,
                        redemptionCode = null
                    )
                )
            )
            .setNotes("This is a note.")

        return paymentGateway.initAIOTransaction(request).also {
            if (it.isSuccess()) {
                val requestId = it.get().requestId
                // Observe transaction result
                observeTransactionResult(requestId)
            }
        }
    }

    /**
     * This function is example for get all installment partners,
     *
     * @return
     */
    private suspend fun getInstallmentPartners(): Result<InstallmentPartnersResult, Throwable> {
        return paymentGateway.getInstallmentPartners(
            InstallmentPartnersBuilder()
                .setTerminalCode("terminalCode")
                .setMerchantCode("merchantCode")
        )
    }

    /**
     * This function is example for register merchant method code when TID and MID is not defined.
     * Now, it is used for method [PaymentMethod.MethodType.QRCodeCustomer]
     */
    private suspend fun registerMMC(): Result<RegisterMMCResult, Throwable> {
        val request = RegisterMMCBuilder()
            // using terminal as default
            .addMethods(PaymentMethod.MethodType.QRCodeCustomer)
            .addMethods(PaymentMethod.MethodType.NONE)
            // force pair terminal and method
            .addPairMethods(
                RegisterMMCBuilder.Method(
                    "TerminalCode",
                    PaymentMethod.MethodType.QRCodeCustomer
                )
            )

        return paymentGateway.registerMMC(request)
    }

    /**
     * This function is used in flow init transaction with method [SPosCardMethodRequest]
     */
    private suspend fun confirmTransaction(): Result<ConfirmTransactionResult, Throwable> {
        val request = ConfirmTransactionBuilder()
            .setTransactionCode("transactionCode")
            .setStatus(ConfirmTransactionResult.StatusTransaction.UnspecifiedStatus)

        return paymentGateway.confirmTransaction(request)
    }

    /**
     * This function is example to get transactions information.
     *
     * @return a list of [TransactionsResult.Transaction]
     */
    private suspend fun getTransactions(): Result<TransactionsResult, Throwable> {
        val requestBuilder = TransactionsRequestBuilder()
            .setFilterByStatuses(arrayListOf(TransactionStatus.Success))

        return paymentGateway.getTransactions(requestBuilder)
    }

    /**
     * This function is example for get list banks that is supported.
     *
     * @return a list of [AvailableBank]
     */
    private suspend fun getBanksSupported(): Result<List<AvailableBank>, Throwable> {
        return paymentGateway.getBanksSupported()
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
        AlertDialog.Builder(this).setTitle("Transaction Result")
            .setMessage("The transaction with requestId: $requestId is $result")
            .setPositiveButton("OK") { _, _ ->
                //TODO: handle process when receive transaction result
            }
            .show()
    }

    /**
     * This function is invoked when merchant has a new transaction is init.
     */
    private fun observeTransactionInit() {
        paymentGateway.observePaymentInit()
            .onEach { requests ->
                showPopupNewTransaction()
            }.launchIn(lifecycleScope)
    }

    private fun showPopupNewTransaction() {
        AlertDialog.Builder(this).setTitle("New Transaction")
            .setMessage("There is new transaction. Do you want process?")
            .setPositiveButton("Next") { _, _ ->
                //TODO: handle process new transaction
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}