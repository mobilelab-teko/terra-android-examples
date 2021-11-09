package com.example.tracker_android.ui.fragment.viewpager

import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentViewPagerBinding
import com.example.tracker_android.ui.fragment.BaseFragment

class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>() {

    override val layoutId: Int = R.layout.fragment_view_pager

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val tabsData = mutableListOf("Tab-1", "Tab-2", "Tab-3")

    override fun initViews() {
        println("ViewPagerFragment.initViews")
        viewPagerAdapter = ViewPagerAdapter(tabsData, childFragmentManager)

        viewDataBinding?.apply {
            viewPager.adapter = viewPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

}