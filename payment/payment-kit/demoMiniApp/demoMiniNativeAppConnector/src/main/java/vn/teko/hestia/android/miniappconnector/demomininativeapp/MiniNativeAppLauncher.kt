package vn.teko.hestia.android.miniappconnector.demomininativeapp

import android.app.Application
import android.content.Intent
import vn.teko.hestia.android.demomininativeapp.MiniAppActivity
import vn.teko.hestia.android.demomininativeapp.MiniAppActivity.Companion.EXTRA_KEY_DUMMY_STRING
import vn.teko.hestia.android.demomininativeapp.MiniAppActivity.Companion.EXTRA_KEY_ID_TOKEN
import vn.teko.hestia.android.model.AppLauncherData
import vn.teko.hestia.android.nativelib.AndroidNativeAppLauncher
import vn.teko.hestia.core.model.authentication.idtoken.IdTokenExchangeAuthResult

class MiniNativeAppLauncher : AndroidNativeAppLauncher {

    override suspend fun create(application: Application, launcherData: AppLauncherData): Intent {
        val intent = Intent(application, MiniAppActivity::class.java)

        val authResult = launcherData.authResult as? IdTokenExchangeAuthResult
        authResult?.let {
            intent.putExtra(EXTRA_KEY_ID_TOKEN, authResult.idToken)
        }

        intent.putExtra(EXTRA_KEY_DUMMY_STRING, launcherData.extraConfig["dummyString"])

        return intent
    }

}