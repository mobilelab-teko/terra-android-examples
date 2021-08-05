package vn.teko.android.terra.demo.auth_ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import vn.teko.android.auth.facebook.loginWithFacebook
import vn.teko.android.auth.google.loginWithGoogle
import vn.teko.android.terra.auth.TerraAuth
import vn.teko.login.core.ui.LoginCallback
import vn.teko.terra.core.android.terra.TerraApp
import vn.teko.android.core.util.Result

class MainActivity : AppCompatActivity() {

    private val terraApp = TerraApp.getInstance()
    private var isLoggedIn = MutableLiveData<Boolean>()

    private var loginFacebookButton: Button? = null
    private var loginGoogleButton: Button? = null
    private var loginUsernamePasswordButton: Button? = null
    private var loginPhoneButton: Button? = null
    private var logoutButton: Button? = null

    private val loginCallback: LoginCallback = object : LoginCallback {

        override fun onResult(result: Result<Unit, Throwable>) {
            when (result) {
                is Result.Success -> {
                    Toast.makeText(this@MainActivity, R.string.login_success, Toast.LENGTH_LONG).show()
                    updateLoginStatus()
                }
                is Result.Failure -> {
                    Toast.makeText(this@MainActivity, R.string.login_fail, Toast.LENGTH_LONG).show()
                    updateLoginStatus()
                }
            }
        }

        override fun onIAMLoginStart() {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        updateLoginStatus()

        isLoggedIn.observe(this, Observer {
            setupButtonsVisible(it)
        })
    }

    private fun initViews() {
        loginFacebookButton = findViewById(R.id.btnLoginFacebook)
        loginFacebookButton?.setOnClickListener {
            TerraAuth.getInstance(terraApp).loginWithFacebook(this, loginCallback)
        }

        loginGoogleButton = findViewById(R.id.btnLoginGoogle)
        loginGoogleButton?.setOnClickListener {
            TerraAuth.getInstance(terraApp).loginWithGoogle(this, loginCallback)
        }

        loginUsernamePasswordButton = findViewById(R.id.btnLoginUsername)
        loginUsernamePasswordButton?.setOnClickListener {
            Intent(this, LoginUsernamePasswordActivity::class.java).let {
                startActivityForResult(it, REQUEST_CODE_1)
            }
        }

        loginPhoneButton = findViewById(R.id.btnLoginPhone)
        loginPhoneButton?.setOnClickListener {
            Intent(this, LoginPhoneActivity::class.java).let {
                startActivityForResult(it, REQUEST_CODE_2)
            }
        }

        logoutButton = findViewById(R.id.btnLogout)
        logoutButton?.setOnClickListener {
            TerraAuth.getInstance(terraApp).logOut {
                updateLoginStatus()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_1 || requestCode == REQUEST_CODE_2) {
            updateLoginStatus()
        }
    }

    private fun setupButtonsVisible(isLoggedIn: Boolean) {
        loginFacebookButton?.isVisible = !isLoggedIn
        loginGoogleButton?.isVisible = !isLoggedIn
        loginUsernamePasswordButton?.isVisible = !isLoggedIn
        loginPhoneButton?.isVisible = !isLoggedIn
        logoutButton?.isVisible = isLoggedIn
    }

    private fun updateLoginStatus() {
        TerraAuth.getInstance(terraApp).isAuthorized {
            isLoggedIn.value = it
        }
    }

    companion object {
        private const val REQUEST_CODE_1 = 1
        private const val REQUEST_CODE_2 = 2
    }

}