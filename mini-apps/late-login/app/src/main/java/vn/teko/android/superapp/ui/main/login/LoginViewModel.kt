package vn.teko.android.superapp.ui.main.login

import androidx.lifecycle.MutableLiveData
import vn.teko.android.auth.data.model.User
import vn.teko.android.core.ui.base.BaseViewModel
import vn.teko.android.core.ui.util.ViewState.LOADING
import vn.teko.android.core.ui.util.ViewState.SUCCESS
import vn.teko.android.core.util.EventBus
import vn.teko.android.terra.auth.TerraAuth
import javax.inject.Inject

class LoginViewModel @Inject constructor() : BaseViewModel<LoginView>() {
    private val _user = MutableLiveData<User?>()

    companion object {
        private const val subscribeId = "uniqueId"
    }

    init {
        fetchUser()
    }

    fun getUser() = _user

    private fun fetchUser() {
        setState(LOADING)
        TerraAuth.getInstance().getUser {
            setState(SUCCESS)
            _user.postValue(it)
        }
    }

    override fun onCleared() {
        EventBus.unsubscribe(subscribeId)
        super.onCleared()
    }

    fun loginGoogle() {
        getView()?.loginGoogle()
    }

    fun logOut() {
        TerraAuth.getInstance().logOut {
            println("Logout-${it}")
            _user.value = null
        }
    }

}