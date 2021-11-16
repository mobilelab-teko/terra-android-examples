package vn.teko.hestia.android.miniappconnector.demomininativeapp

import android.app.Application
import android.content.Intent
import vn.teko.hestia.android.demomininativeapp.MiniAppActivity
import vn.teko.hestia.android.model.AppLauncherData
import vn.teko.hestia.android.nativelib.AndroidNativeAppLauncher
import vn.teko.hestia.core.model.authentication.idtoken.IdTokenExchangeAuthResult

class MiniNativeAppLauncher : AndroidNativeAppLauncher {

    override suspend fun create(application: Application, launcherData: AppLauncherData): Intent {
        return parseDataThen(launcherData) { displayString, idToken ->
            MiniAppActivity.createIntent(
                context = application, config = MiniAppActivity.Config(
                    idToken = idToken,
                    displayString = displayString
                )
            )
        }
    }

    private fun <T> parseDataThen(
        launcherData: AppLauncherData,
        block: (displayString: String, idToken: String?) -> T
    ): T {
        val displayString =
            "This is extra data: " + launcherData.extraConfig.keys.joinToString { "\n${it}=${launcherData.extraConfig[it]}" }
        val idToken = if (launcherData.authResult is IdTokenExchangeAuthResult) {
            (launcherData.authResult as? IdTokenExchangeAuthResult)?.idToken
        } else {
            null
        }

        return block(displayString, idToken)
    }

}