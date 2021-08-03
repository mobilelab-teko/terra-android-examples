package vn.teko.android.terra.demo.auth_ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import vn.teko.android.auth.login.ui.TerraAuthUI
import vn.teko.android.terra.auth.TerraAuth
import vn.teko.terra.core.android.terra.TerraApp

class MainActivity : AppCompatActivity() {

    private val terraApp = TerraApp.getInstance()
    private val authUi = TerraAuthUI.getInstance(terraApp)
    private var isLoggedIn = MutableLiveData<Boolean>(null)

    private var loginButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton = findViewById<Button>(R.id.loginBtn)

        updateLoginStatus()

        isLoggedIn.observe(this, Observer {
            when (it) {
                false -> setupLoginButton()
                true -> setupLogoutButton()
                else -> hideLoginButton()
            }
        })
    }

    private fun setupLoginButton() {
        loginButton?.isVisible = true
        loginButton?.text = getString(R.string.login)
        loginButton?.setOnClickListener {
            authUi.setLogoResource(R.drawable.ic_action_name)
            authUi.startLogin(this)
        }
    }

    private fun setupLogoutButton() {
        loginButton?.isVisible = true
        loginButton?.text = getString(R.string.logout)
        loginButton?.setOnClickListener {
            authUi.logOut {
                Toast.makeText(this, R.string.logged_out, Toast.LENGTH_LONG).show()
                updateLoginStatus()
            }
        }
    }

    private fun hideLoginButton() {
        loginButton?.isVisible = false
    }

    private fun updateLoginStatus() {
        TerraAuth.getInstance(terraApp).isAuthorized {
            isLoggedIn.value = it
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TerraAuthUI.LOGIN_UI_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // handle successful login
                Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show()
                updateLoginStatus()
            } else {
                // handle failed login
                Toast.makeText(this, R.string.login_fail, Toast.LENGTH_LONG).show()
                updateLoginStatus()
            }
        }
    }
}