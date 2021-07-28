package vn.teko.terra.demo.paymentcore.other

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_other_apis.*
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.teko.android.core.util.Result
import vn.teko.android.core.util.android.extension.showToast
import vn.teko.android.payment.manager.TerraPayment
import vn.teko.android.payment.v2.model.api.response.AvailableBank
import vn.teko.android.payment.v2.model.exposing.builder.ConfirmTransactionBuilder
import vn.teko.android.payment.v2.model.exposing.builder.InstallmentPartnersBuilder
import vn.teko.android.payment.v2.model.exposing.builder.RegisterMMCBuilder
import vn.teko.android.payment.v2.model.exposing.payment.methods.card.SPosCardMethodRequest
import vn.teko.android.payment.v2.model.exposing.result.ConfirmTransactionResult
import vn.teko.android.payment.v2.model.exposing.result.InstallmentPartnersResult
import vn.teko.android.payment.v2.model.exposing.result.RegisterMMCResult
import vn.teko.android.payment.v2.model.exposing.result.payment.method.PaymentMethod
import vn.teko.terra.core.android.terra.TerraApp
import vn.teko.terra.demo.paymentcore.R

class OtherAPIsFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_other_apis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegisterMMC.setOnClickListener {
            lifecycleScope.launch { registerMMC() }
        }

        btnConfirmTransaction.setOnClickListener {
            lifecycleScope.launch { confirmTransaction() }
        }

        btnGetAllBanksSupported.setOnClickListener {
            lifecycleScope.launch { getBanksSupported() }
        }

        btnGetInstallmentPartners.setOnClickListener {
            lifecycleScope.launch { getInstallmentPartners() }
        }
    }

    private fun showPopup(title: String, message: String) {
        AlertDialog.Builder(requireContext()).setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .show()
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

        return paymentGateway.registerMMC(request).also { result ->
            if (result.isSuccess() && result.getOrNull() != null) {
                showToast("Register MMC successfully", duration = Toast.LENGTH_LONG)
                Timber.i("Register MMC success")
            } else {
                showToast(
                    "Register MMC failed ${result.exceptionOrNull()?.message}",
                    duration = Toast.LENGTH_LONG
                )
                Timber.e(result.exceptionOrNull())
            }
        }
    }

    /**
     * This function is used in flow init transaction with method [SPosCardMethodRequest]
     * @required transactionCode: The transaction code of transaction need confirming
     */
    private suspend fun confirmTransaction(): Result<ConfirmTransactionResult, Throwable> {
        val request = ConfirmTransactionBuilder()
            .setTransactionCode("transactionCode")
            .setStatus(ConfirmTransactionResult.StatusTransaction.UnspecifiedStatus)

        return paymentGateway.confirmTransaction(request).also { result ->
            if (result.isSuccess() && result.getOrNull() != null) {
                showPopup(
                    title = "Confirm Transaction Success",
                    message = result.get().message
                )
                Timber.i(result.get().message)
            } else {
                showToast("Confirm Transaction fail")
                Timber.e(result.exceptionOrNull())
            }
        }
    }

    /**
     * This function is example for get list banks that is supported.
     *
     * @return a list of [AvailableBank]
     */
    private suspend fun getBanksSupported(): Result<List<AvailableBank>, Throwable> {
        return paymentGateway.getBanksSupported().also { result ->
            if (result.isSuccess() && result.getOrNull() != null) {
                val banksSupported = result.get().joinToString(transform = { it.name })
                showPopup(
                    title = "Get List Banks Supported success",
                    message = "Available bank: $banksSupported"
                )
                Timber.i(result.get().toString())
            } else {
                showToast("Get banks supported fail")
                Timber.e(result.exceptionOrNull())
            }
        }
    }

    /**
     * This function is example for get all installment partners,
     * It depend on terminalCode, merchantCode that is init in [paymentGateway]
     * (they are also updated)
     *
     * @return
     */
    private suspend fun getInstallmentPartners(): Result<InstallmentPartnersResult, Throwable> {
        return paymentGateway.getInstallmentPartners(
            InstallmentPartnersBuilder()
                .setTerminalCode(paymentGateway.config.getTerminalCode())
                .setMerchantCode(paymentGateway.config.getMerchantCode())
        ).also { result ->
            if (result.isSuccess() && result.getOrNull() != null) {
                val partners = result.get().installmentPartners
                    .joinToString(transform = { it.name ?: it.code ?: "" })
                showPopup(
                    title = "Get Installment Partners success",
                    message = "Available partners: $partners"
                )
                Timber.i(result.get().toString())
            } else {
                showToast("Get list Installment Partners fail")
                Timber.e(result.exceptionOrNull())
            }
        }
    }
}