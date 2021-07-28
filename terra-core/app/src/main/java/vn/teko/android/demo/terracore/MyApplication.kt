package vn.teko.android.demo.terracore

import android.app.Application
import vn.teko.terra.core.android.pandora.model.TerraConfig
import vn.teko.terra.core.android.pandora.model.TerraConfig.TerraEnv
import vn.teko.terra.core.android.terra.TerraApp
import vn.teko.terra.core.android.terra.TerraState

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        // It's important to initialize TerraApp inside Application.onCreate
        // So TerraApp will always be re-initialized when system destroys your Activities
        val terraApp = when (initMode) {
            DemoInitMode.Default -> {
                // will load config from assets/TerraService-[DEFAULT].json
                TerraApp.initializeApp(application = this)
            }
            DemoInitMode.WithAppName -> {
                // will load config from assets/TerraService-demo.json
                TerraApp.initializeApp(application = this, appName = DEMO_TERRA_APP_NAME)
            }
            DemoInitMode.WithoutConfigFile -> {
                // will load config from terraConfig parameter
                TerraApp.initializeApp(application = this, terraConfig = TerraConfig(
                    terraClientId = "demosuperapp:android:playstore:0.0.1-payment-kit-demo",
                    terraEnvironment = TerraEnv.Develop
                ))
            }
        }

        if (!terraApp.isInitialized()) {
            throw Exception("TerraApp is not initialized. Error: ${(terraApp.getState() as? TerraState.InitializeFailed)?.error?.message}")
        }

    }

    enum class DemoInitMode {
        Default,
        WithAppName,
        WithoutConfigFile
    }

    companion object {

        // change this to try different init mode
        val initMode: DemoInitMode = DemoInitMode.Default

        const val DEMO_TERRA_APP_NAME: String = "demo"
    }

}