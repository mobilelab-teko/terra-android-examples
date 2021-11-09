package com.example.tracker_android.ui.fragment.viewpager

import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentPageBinding
import com.example.tracker_android.tracking.AppScreenName
import com.example.tracker_android.ui.fragment.BaseTrackableFragment
import vn.teko.android.tracker.event.body.ScreenViewEventBody

class PageFragment : BaseTrackableFragment<FragmentPageBinding>() {

    override fun screenName(): ScreenViewEventBody.ScreenName {
        return AppScreenName.ViewPage(key)
    }

    override val layoutId: Int = R.layout.fragment_page

    private var key: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        key = arguments?.getString(CONTENT_TEXT_KEY, "") ?: ""

        super.onCreate(savedInstanceState)
    }

    override fun initViews() {
        println("PageFragment.initViews")
        viewDataBinding?.tvContent?.text = key
    }

    companion object {
        private const val CONTENT_TEXT_KEY = "content_text_key"

        fun newInstance(type: String): PageFragment {
            val bundle = bundleOf(Pair(CONTENT_TEXT_KEY, type))
            val fragment = PageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}