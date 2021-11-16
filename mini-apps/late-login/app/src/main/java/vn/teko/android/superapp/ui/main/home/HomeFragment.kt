package vn.teko.android.superapp.ui.main.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import vn.teko.android.superapp.BR
import vn.teko.android.superapp.R
import vn.teko.android.superapp.databinding.FragmentHomeBinding
import vn.teko.android.superapp.ui.base.BaseAppFragment
import vn.teko.android.superapp.utils.extensions.showToast
import vn.teko.hestia.android.terra.TerraHestia
import vn.teko.hestia.core.HestiaCallback
import vn.teko.hestia.core.model.AppType.NativeAndroid
import vn.teko.hestia.core.util.HestiaError

class HomeFragment : BaseAppFragment<FragmentHomeBinding, HomeViewModel>(),
    HomeView {

    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun getViewModel() = homeViewModel

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.setView(this)
    }

    override fun onResume() {
        homeViewModel.refreshLoginStatus()
        super.onResume()
    }

    override fun initComponents() {

    }

    override fun clearComponents() {

    }

    override fun openMiniApp() {
        showLoading()

        TerraHestia.getInstance().startApp(
            appCode = "mini_app",
            appType = NativeAndroid,
            callback = object : HestiaCallback {
                override fun onSuccess() {
                    hideLoading()
                    showToast("onSuccess")
                }

                override fun onError(error: HestiaError) {
                    hideLoading()
                    showToast("onError ${error.javaClass}")
                }
            }
        )
    }

    override fun goToLogin() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }

    override fun showError(e: Throwable) {
        e.message?.let {
            showToast(it)
        }
    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            showLoading()
        } else {
            hideLoading()
        }
    }
}