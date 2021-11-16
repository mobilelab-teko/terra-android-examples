package vn.teko.android.superapp

import android.content.Intent
import vn.teko.android.core.ui.BaseApplication
import vn.teko.android.superapp.di.component.DaggerAppComponent
import vn.teko.android.superapp.ui.loginContainer.LoginContainerActivity
import vn.teko.hestia.android.terra.TerraHestia
import vn.teko.hestia.controller.auth.TerraAuthController
import vn.teko.terra.core.android.terra.TerraApp

class SuperApp : BaseApplication() {

    override fun performDependencyInjection() {
        TerraApp.initializeApp(application = this).let {
            TerraHestia.getInstance(it)
            TerraAuthController.getInstance(it)
                .registerLoginIntent(Intent(this, LoginContainerActivity::class.java))
        }

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun getVersion(): String = BuildConfig.VERSION_NAME

}
