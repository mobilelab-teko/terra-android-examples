package vn.teko.android.superapp.ui.loginContainer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import vn.teko.android.core.ui.base.BaseActivity
import vn.teko.android.superapp.BR
import vn.teko.android.superapp.R.*
import vn.teko.android.superapp.databinding.ActivityLoginContainerBinding
import vn.teko.android.superapp.databinding.ActivityMainBinding
import vn.teko.android.superapp.ui.main.login.LoginFragmentArgs

class LoginContainerActivity : BaseActivity<ActivityLoginContainerBinding, LoginContainerViewModel>() {

    private val loginContainerViewModel: LoginContainerViewModel by viewModels { viewModelFactory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId() = layout.activity_login_container

    override fun getViewModel(): LoginContainerViewModel = loginContainerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNavGraph(navigation.login_container)
    }

    override fun initComponents() {

    }

    private fun setNavGraph(navigationGraphId: Int) {
        (supportFragmentManager.findFragmentById(id.mainHostFragment) as NavHostFragment?)?.let {
            val startArg = LoginFragmentArgs(isStandalone = true).toBundle()
            val graph = it.navController.navInflater.inflate(navigationGraphId)
            it.navController.setGraph(graph, startArg)
        }
    }

}