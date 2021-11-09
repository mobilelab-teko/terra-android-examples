package com.example.tracker_android.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber

abstract class BaseBottomSheet<T : ViewDataBinding> : BottomSheetDialogFragment() {

    protected var viewDataBinding: T? = null

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding =
            DataBindingUtil.inflate(layoutInflater, layoutId, null, false)

        initViews()

        return viewDataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onViewCreated")
    }

    protected open fun initViews() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onDestroy")
    }

}