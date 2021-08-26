package vn.teko.terra.demo.miniapp.ui.home

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
import vn.teko.android.core.util.android.extension.showProgressDialog
import vn.teko.android.terra.auth.TerraAuth
import vn.teko.hestia.android.terra.TerraHestia
import vn.teko.hestia.connector.example.data.StartingMiniAppHistory
import vn.teko.hestia.core.HestiaCallback
import vn.teko.hestia.core.HestiaInterface
import vn.teko.hestia.core.model.AppType
import vn.teko.hestia.core.util.HestiaError
import vn.teko.terra.core.android.terra.TerraApp
import vn.teko.terra.demo.miniapp.R
import vn.teko.terra.demo.miniapp.databinding.FragmentHomeBinding
import vn.teko.terra.demo.miniapp.utils.extensions.showMessageDialog
import vn.teko.terra.demo.miniapp.utils.extensions.showProgressDialog

class HomeFragment : Fragment() {

    private val terraApp: TerraApp by lazy {
        TerraApp.getInstance()
    }

    private val authManager: AuthManagerInterface by lazy {
        TerraAuth.getInstance(terraApp)
    }

    private val hestia: HestiaInterface by lazy {
        TerraHestia.getInstance(terraApp)
    }

    private var isAuthorized: Boolean = false

    private var viewDataBinding by autoCleared<FragmentHomeBinding>()

    private var loadingDialog: AlertDialog? = null

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
            updateButtonsVisibility()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.edtAppCode.setText(
            StartingMiniAppHistory.getLastStartedMiniAppCode(requireContext())
        )

        viewDataBinding.btnStartMiniApp.setOnClickListener {
            startMiniApp()
        }

        viewDataBinding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        viewDataBinding.btnLogout.setOnClickListener {
            TerraAuth.getInstance(terraApp).logOut {
                if (it) {
                    isAuthorized = false
                    updateButtonsVisibility()
                }
            }
        }
    }

    private fun updateButtonsVisibility() {
        viewDataBinding.btnLogin.isVisible = !isAuthorized
        viewDataBinding.btnLogout.isVisible = isAuthorized
    }

    private fun startMiniApp() {
        val miniAppCode = viewDataBinding.edtAppCode.text.toString()

        if (miniAppCode.isEmpty()) {
            AlertDialog.Builder(requireContext())
                .setMessage("Please enter the app code before starting the mini app")
                .setNegativeButton("OK") { _, _ -> }
                .show()
            return
        }

        StartingMiniAppHistory.saveLastStartedMiniAppCode(requireContext(), miniAppCode)

        showStartingAppDialog()

        hestia.startApp(
            appCode = miniAppCode,
            appType = AppType.ReactNativeAndroid,
            callback = object : HestiaCallback {

                override fun onError(error: HestiaError) {
                    hideStartingAppDialog()

                    showStartingAppError(error)
                }

                override fun onSuccess() {
                    hideStartingAppDialog()
                }

            }
        )
    }

    private fun showStartingAppDialog() {
        loadingDialog = showProgressDialog()
    }

    private fun hideStartingAppDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showStartingAppError(error: HestiaError) {

        val errorMessage = when (error) {
            is HestiaError.MiniAppNotFound -> "App not found"

            is HestiaError.MultipleAppsFound -> "Multiple apps found"

            is HestiaError.MissingAccessToken -> "Not logged in"

            is HestiaError.ExchangeTokenFailed -> "Exchange token failed"

            is HestiaError.MiniAppDelegateNotFound -> "Delegate not found"

            else -> error.message
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Cannot start the mini-app")
            .setMessage(errorMessage)
            .setNegativeButton("OK") { _, _ -> }
            .show()

    }
}