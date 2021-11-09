package com.example.tracker_android.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.tracker_android.BuildConfig
import timber.log.Timber
import vn.teko.android.core.util.instancesmanager.AppIdentifier

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), AppIdentifier {

    protected var viewDataBinding: T? = null

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding =
            DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
        setContentView(viewDataBinding!!.root)

        Timber.log(Log.DEBUG, "[TRACKER_INFO] ${javaClass.canonicalName} onCreate")

        initViews()
    }

    override fun onStart() {
        super.onStart()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] ${javaClass.canonicalName} onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] ${javaClass.canonicalName} onResume")
    }

    protected open fun initViews() {

    }

    override fun onStop() {
        super.onStop()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] ${javaClass.canonicalName} onStop")
    }

    override fun onPause() {
        super.onPause()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] ${javaClass.canonicalName} onPause")
    }

    override fun onDestroy() {
        viewDataBinding = null
        super.onDestroy()
        Timber.log(Log.DEBUG, "[TRACKER_INFO] ${javaClass.canonicalName} onDestroy")
    }

    override val appIdentifier: String = BuildConfig.APPLICATION_ID
}