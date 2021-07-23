package vn.teko.hestia.android.miniappconnector.demomininativeapp

import android.app.Application
import android.content.Intent
import vn.teko.hestia.android.demomininativeapp.MiniAppActivity
import vn.teko.hestia.android.model.AppLauncherData
import vn.teko.hestia.android.nativelib.AndroidNativeAppLauncher

class MiniNativeAppLauncher : AndroidNativeAppLauncher {

    override suspend fun create(application: Application, launcherData: AppLauncherData): Intent {
        return Intent(application, MiniAppActivity::class.java)
    }

}