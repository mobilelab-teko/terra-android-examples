package vn.teko.android.superapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import vn.teko.android.core.ui.base.BaseActivity
import vn.teko.android.superapp.BR
import vn.teko.android.superapp.R.*
import vn.teko.android.superapp.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId() = layout.activity_main

    override fun getViewModel(): MainViewModel = mainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavGraph(navigation.main)
    }

    override fun initComponents() {
    }

    private fun setNavGraph(navigationGraphId: Int) {
        (supportFragmentManager.findFragmentById(id.mainHostFragment) as NavHostFragment?)?.let {
            val graph = it.navController.navInflater.inflate(navigationGraphId)
            it.navController.graph = graph
        }
    }
}