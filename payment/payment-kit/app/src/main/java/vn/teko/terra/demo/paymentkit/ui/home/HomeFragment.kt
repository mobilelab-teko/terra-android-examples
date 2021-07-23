package vn.teko.terra.demo.paymentkit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import vn.teko.android.auth.core.AuthManagerInterface
import vn.teko.android.core.ui.util.autoCleared
import vn.teko.android.terra.auth.TerraAuth
import vn.teko.hestia.android.terra.TerraHestia
import vn.teko.hestia.core.HestiaCallback
import vn.teko.hestia.core.model.AppType
import vn.teko.hestia.core.util.HestiaError
import vn.teko.terra.core.android.terra.TerraApp
import vn.teko.terra.demo.paymentkit.R
import vn.teko.terra.demo.paymentkit.databinding.FragmentHomeBinding
import vn.teko.terra.demo.paymentkit.utils.extensions.showMessageDialog
import vn.teko.terra.demo.paymentkit.utils.extensions.showProgressDialog

class HomeFragment : Fragment() {

    private val terraApp: TerraApp by lazy {
        TerraApp.getInstance()
    }

    private val authManager: AuthManagerInterface by lazy {
        TerraAuth.getInstance(terraApp)
    }

    private var isAuthorized: Boolean = false

    private var progressDialog: AlertDialog? = null

    private var viewDataBinding by autoCleared<FragmentHomeBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()

        authManager.isAuthorized {
            isAuthorized = it
            if (!isAuthorized) {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.btnOpenSelfPaymentNativeApp.setOnClickListener {
            openNativeAppWithSelfPayment()
        }

        viewDataBinding.btnOpenHostPaymentNativeApp.setOnClickListener {
            openNativeAppWithHostPayment()
        }

        viewDataBinding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        viewDataBinding.btnLogout.setOnClickListener {
            logOut()
        }
    }

    private fun updateButtonsVisibility() {
        viewDataBinding.btnLogin.isVisible = !isAuthorized
        viewDataBinding.btnLogout.isVisible = isAuthorized

    }

    private fun openNativeAppWithSelfPayment() {
        startMiniApp(SELF_PAYMENT_FLOW_MINI_APP_CODE)
    }

    private fun openNativeAppWithHostPayment() {
        startMiniApp(HOST_PAYMENT_FLOW_MINI_APP_CODE)
    }

    private fun startMiniApp(appCode: String, appType: AppType = AppType.NativeAndroid) {
        showLoading()
        TerraHestia.getInstance(terraApp = terraApp)
            .startApp(
                appCode,
                appType = appType,
                callback = object : HestiaCallback {
                    override fun onError(error: HestiaError) {
                        hideLoading()

                        context.showMessageDialog(
                            title = "Starting app error",
                            message = error.message.orEmpty(),
                            positiveButtonTitle = R.string.ok,
                            positiveButtonAction = { _, _ -> },
                            cancelable = true
                        )
                    }

                    override fun onSuccess() {
                        hideLoading()
                    }
                }
            )
    }

    private fun logOut() {
        TerraAuth.getInstance(terraApp).logOut {
            if (it) {
                isAuthorized = false
                updateButtonsVisibility()
            }
        }
    }

    private fun showLoading() {
        progressDialog = context.showProgressDialog()
    }

    private fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    companion object {
        private const val SELF_PAYMENT_FLOW_MINI_APP_CODE = "mini_app"
        private const val HOST_PAYMENT_FLOW_MINI_APP_CODE = "mini_app_02"
    }
}