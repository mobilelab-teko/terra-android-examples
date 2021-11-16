package vn.teko.hestia.android.demomininativeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlinx.parcelize.Parcelize
import vn.teko.android.auth.kit.AuthKit

class MiniAppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mini_app)

        val config = intent.getParcelableExtra<Config>(EXTRA_KEY_CONFIG)
            ?: throw IllegalArgumentException("Missing config for starting activity")

        // get data through Intent
        findViewById<TextView>(R.id.tvDummy).text = config.displayString
        findViewById<TextView>(R.id.tvContent).text = config.idToken ?: "Not logged in!"

        findViewById<Button>(R.id.btnRequestLogin).setOnClickListener {
            requestHostAppLogin()
        }
    }

    private fun requestHostAppLogin() {
        AuthKit.login(this) {
            if (it.isSuccess()) {
                Toast.makeText(this, "Login success!", Toast.LENGTH_LONG).show()
                findViewById<TextView>(R.id.tvContent).text = it.get()
            } else {
                Toast.makeText(
                    this,
                    "Login failed with error: ${it.exception().message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        private const val EXTRA_KEY_CONFIG = "config"

        @JvmStatic
        fun createIntent(context: Context, config: Config) =
            Intent(context, MiniAppActivity::class.java).apply {
                putExtra(EXTRA_KEY_CONFIG, config)
            }
    }

    @Parcelize
    data class Config(
        val idToken: String?,
        val displayString: String?
    ) : Parcelable

}