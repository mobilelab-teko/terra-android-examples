package vn.teko.terra.demo.paymentcore

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import vn.teko.android.payment.manager.TerraPayment
import vn.teko.terra.core.android.terra.TerraApp

class MainActivity : AppCompatActivity() {

    private val paymentGateway by lazy {
        TerraPayment.getInstance(
            terraApp = TerraApp.getInstance()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeTransactionInit()
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