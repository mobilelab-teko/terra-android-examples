package vn.teko.android.superapp.ui.main.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import vn.teko.android.auth.google.loginWithGoogle
import vn.teko.android.core.util.Result
import vn.teko.android.superapp.BR
import vn.teko.android.superapp.R
import vn.teko.android.superapp.databinding.FragmentLoginBinding
import vn.teko.android.superapp.ui.base.BaseAppFragment
import vn.teko.android.terra.auth.TerraAuth
import vn.teko.hestia.controller.auth.TerraAuthController
import vn.teko.login.core.ui.LoginCallback
import vn.teko.terra.core.android.terra.TerraApp

class LoginFragment : BaseAppFragment<FragmentLoginBinding, LoginViewModel>(),
    LoginView, LoginCallback {

    private val terraApp = TerraApp.getInstance()

    private val loginViewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun getViewModel() = loginViewModel

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_login

    private var isStandalone: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.setView(this)

        isStandalone = arguments?.let {
            LoginFragmentArgs.fromBundle(it).isStandalone
        } ?: false
    }

    override fun initComponents() {

    }

    override fun clearComponents() {

    }

    override fun loginGoogle() {
        showLoading()
        TerraAuth.getInstance().loginWithGoogle(this, this)
    }

    override fun onResult(result: Result<Unit, Throwable>) {
        hideLoading()
        if (isStandalone) {
            TerraAuthController.getInstance(terraApp).finishLogin(requireActivity(), result)
        } else {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        if (isStandalone) {
            TerraAuthController.getInstance(terraApp).cancelLogin(requireActivity())
        }

        super.onDestroy()
    }

    override fun onIAMLoginStart() {
        println("onIAMLoginStart")
    }

}