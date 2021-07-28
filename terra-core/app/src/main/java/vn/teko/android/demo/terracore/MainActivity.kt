package vn.teko.android.demo.terracore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import vn.teko.android.demo.terracore.MyApplication.DemoInitMode
import vn.teko.terra.core.android.terra.TerraApp
import vn.teko.terra.core.android.terra.TerraApp.Companion

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val terraApp = when (MyApplication.initMode) {
            DemoInitMode.WithAppName -> TerraApp.getInstance(appName = MyApplication.DEMO_TERRA_APP_NAME)
            else -> TerraApp.getInstance()
        }

        // getting custom appConfig
        val appConfig = terraApp.getAppConfig()

        // common config
        setText(R.id.showLogValue, appConfig?.data?.showLogs?.toString())

        // app's specific config
        appConfig?.extra?.let {
            try {
                MyAppConfig.fromMap(it)
            } catch (e: Throwable) {
                null
            }?.let { myAppConfig ->
                setText(R.id.demoStringValue, myAppConfig.demoString)
                setText(R.id.demoNumberValue, myAppConfig.demoJson.demoNumber.toString())
                setText(R.id.demoBooleanValue, myAppConfig.demoJson.demoBoolean.toString())
            }
        }
    }

    private fun setText(id: Int, value: String?) {
        findViewById<TextView>(id)?.text = value ?: "undefined"
    }
}