package vn.teko.android.superapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import vn.teko.android.core.ui.base.BaseFragment
import vn.teko.android.core.ui.base.BaseViewModel

abstract class BaseAppFragment<T : ViewDataBinding, VM : BaseViewModel<*>> : BaseFragment<T, VM>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { owner ->
            owner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    clearComponents()
                }
            })
        }
    }
}