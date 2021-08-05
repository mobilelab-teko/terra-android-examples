package vn.teko.android.terra.demo.auth_ui

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import vn.teko.android.auth.login.provider.UsernamePasswordProvider
import vn.teko.android.terra.auth.TerraAuth
import vn.teko.terra.core.android.terra.TerraApp

class LoginUsernamePasswordActivity : AppCompatActivity() {

    private val terraApp = TerraApp.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login_username_password)

        findViewById<Button>(R.id.btnNext)?.setOnClickListener {
            val username = findViewById<TextInputLayout>(R.id.username)?.editText?.text
            val password = findViewById<TextInputLayout>(R.id.password)?.editText?.text

            if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
                val credential = UsernamePasswordProvider.getLoginCredential(username.toString(), password.toString())

                TerraAuth.getInstance(terraApp).loginWithCredential(credential) {
                    if (it.isSuccess()) {
                        Toast.makeText(
                            this,
                            R.string.login_success,
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            R.string.login_fail,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    R.string.error_missing_data,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


}