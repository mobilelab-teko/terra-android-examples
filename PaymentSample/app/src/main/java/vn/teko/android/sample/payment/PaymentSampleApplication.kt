package vn.teko.android.sample.payment

import android.app.Application
import vn.teko.android.payment.manager.TerraPayment
import vn.teko.android.payment.v2.IPaymentGateway
import vn.teko.apollo.terra.TerraApollo
import vn.teko.terra.core.android.terra.TerraApp

class PaymentSampleApplication: Application() {

    lateinit var paymentGateway: IPaymentGateway

    companion object {

        lateinit var shared: PaymentSampleApplication
    }

    override fun onCreate() {
        super.onCreate()
        shared = this
        TerraApp.initializeApp(this).apply {
            TerraApollo.getInstance(this)
        }
        paymentGateway = TerraPayment.getInstance(TerraApp.getInstance())
    }
}
