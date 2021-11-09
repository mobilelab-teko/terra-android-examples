package com.example.tracker_android.ui.fragment.tabnavigation

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentTabNavigationBinding
import com.example.tracker_android.ui.fragment.BaseFragment

class TabNavigationFragment : BaseFragment<FragmentTabNavigationBinding>() {

    override val layoutId: Int = R.layout.fragment_tab_navigation

    override fun initViews() {
        println("TabNavigationFragment.initViews")

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment

        NavigationUI.setupWithNavController(
            viewDataBinding!!.bottomNav,
            navHostFragment.navController
        )

    }

}