package vn.teko.terra.demo.paymentcore

import android.app.Application
import vn.teko.android.payment.manager.TerraPayment
import vn.teko.android.payment.v2.IPaymentGateway
import vn.teko.terra.core.android.terra.TerraApp

class ExampleApp: Application() {

    lateinit var paymentGateway: IPaymentGateway

    override fun onCreate() {
        super.onCreate()

        TerraApp.initializeApp(this).apply {
            paymentGateway = TerraPayment.getInstance(this)
        }
    }
}