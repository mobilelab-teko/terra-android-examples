package com.example.tracker_android.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected var viewDataBinding: T? = null

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onCreate")
    }

    override fun onStart() {
        super.onStart()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onResume")
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

    override fun onPause() {
        super.onPause()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] $this onDestroy")
    }
}