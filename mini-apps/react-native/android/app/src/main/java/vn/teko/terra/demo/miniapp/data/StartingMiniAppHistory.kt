package vn.teko.hestia.connector.example.data

import android.content.Context

object StartingMiniAppHistory {

    private const val SHARED_PREFERENCES_NAME = "connector-demo-app"
    private const val KEY_LAST_STARTED_MINI_APP_CODE = "last-started-mini-app-code"

    fun getLastStartedMiniAppCode(context: Context): String? {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        return sharedPreferences.getString(KEY_LAST_STARTED_MINI_APP_CODE, null)
    }

    fun saveLastStartedMiniAppCode(context: Context, miniAppCode: String) {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        sharedPreferences.edit().putString(KEY_LAST_STARTED_MINI_APP_CODE, miniAppCode).apply()
    }

}