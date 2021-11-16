package vn.teko.android.superapp.ui.main.home

import androidx.lifecycle.MutableLiveData
import vn.teko.android.core.ui.base.BaseViewModel
import vn.teko.android.terra.auth.TerraAuth
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel<HomeView>() {

    val isLoggedIn = MutableLiveData<Boolean>(false)

    init {
        refreshLoginStatus()
    }

    fun refreshLoginStatus() {
        getView()?.toggleLoading(true)
        TerraAuth.getInstance().isAuthorized {
            getView()?.toggleLoading(false)
            isLoggedIn.postValue(it)
        }
    }

    fun openMiniApp() {
        getView()?.openMiniApp()
    }

    fun goToLogin() {
        getView()?.goToLogin()
    }

    fun logOut() {
        TerraAuth.getInstance().logOut {
            if (it) {
                isLoggedIn.postValue(false)
            }
        }
    }
}