package vn.teko.terra.demo.paymentkit.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import vn.teko.android.auth.core.AuthCoreInterface
import vn.teko.android.auth.google.loginWithGoogle
import vn.teko.android.core.ui.util.autoCleared
import vn.teko.android.core.util.Result
import vn.teko.android.terra.auth.TerraAuth
import vn.teko.login.core.ui.LoginCallback
import vn.teko.terra.core.android.terra.TerraApp
import vn.teko.terra.demo.paymentkit.R
import vn.teko.terra.demo.paymentkit.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var authManager: AuthCoreInterface

    private var viewDataBinding by autoCleared<FragmentLoginBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authManager = TerraAuth.getInstance(TerraApp.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disableBackPress()

        viewDataBinding.btnLoginGoogle.setOnClickListener {
            loginWithGoogle()
        }

    }

    private fun loginWithGoogle() {
        authManager.loginWithGoogle(this, object : LoginCallback {
            override fun onResult(result: Result<Unit, Throwable>) {
                if (result.isSuccess()) {
                    navigateBack()
                } else {
                    authManager.logOut { }
                }
            }

            override fun onIAMLoginStart() {

            }
        })
    }

    private fun disableBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {}
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }
}