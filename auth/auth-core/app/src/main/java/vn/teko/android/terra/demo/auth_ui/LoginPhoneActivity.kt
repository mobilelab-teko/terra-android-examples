package vn.teko.android.terra.demo.auth_ui

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import vn.teko.android.auth.login.provider.PhoneProvider
import vn.teko.android.auth.login.provider.UsernamePasswordProvider
import vn.teko.android.terra.auth.TerraAuth
import vn.teko.terra.core.android.terra.TerraApp

class LoginPhoneActivity : AppCompatActivity() {

    private val terraApp = TerraApp.getInstance()

    private var isWaitingOtp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_phone)

        findViewById<Button>(R.id.btnNext)?.setOnClickListener {
            if (!isWaitingOtp) {
                // phase 1: request OTP
                val phone = findViewById<TextInputLayout>(R.id.phone)?.editText?.text
                if (!phone.isNullOrBlank()) {
                    PhoneProvider.requestPhoneOtp(phone.toString(), TerraAuth.getInstance(terraApp)) {
                        if (it.isSuccess()) {
                            Toast.makeText(
                                this,
                                R.string.request_otp_success,
                                Toast.LENGTH_LONG
                            ).show()

                            enableOtpInput()
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
            } else {
                // phase 2: login with phone & otp
                val phone = findViewById<TextInputLayout>(R.id.phone)?.editText?.text
                val otp = findViewById<TextInputLayout>(R.id.otp)?.editText?.text

                if (!phone.isNullOrBlank() && !otp.isNullOrBlank()) {
                    val credential =
                        PhoneProvider.getLoginCredential(phone.toString(), otp.toString())
                    TerraAuth.getInstance(terraApp)
                        .loginWithCredential(credential) {
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

    private fun enableOtpInput() {
        isWaitingOtp = true
        findViewById<TextInputLayout>(R.id.otp)?.editText?.isEnabled = true
        findViewById<TextInputLayout>(R.id.phone)?.editText?.isEnabled = false
        findViewById<Button>(R.id.btnNext)?.text = getString(R.string.login)
    }


}