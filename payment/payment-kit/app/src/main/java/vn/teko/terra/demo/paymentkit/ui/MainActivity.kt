package vn.teko.terra.demo.paymentkit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.teko.android.core.util.instancesmanager.AppIdentifier
import vn.teko.terra.core.android.terra.TerraApp
import vn.teko.terra.demo.paymentkit.R

class MainActivity : AppCompatActivity(), AppIdentifier {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override val appIdentifier: String = TerraApp.DEFAULT_APP_NAME

}