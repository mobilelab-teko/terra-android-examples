package com.example.tracker_android.ui.fragment.home

import androidx.navigation.fragment.findNavController
import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentUntrackedBinding
import com.example.tracker_android.ui.fragment.BaseFragment

class UnTrackedFragment : BaseFragment<FragmentUntrackedBinding>() {

    override val layoutId: Int = R.layout.fragment_untracked

    override fun initViews() {
        viewDataBinding?.btnGotoTrackedFragment?.setOnClickListener {
            findNavController().navigate(UnTrackedFragmentDirections.actionUntrackedToTracked())
        }
    }

}