package com.example.tracker_android.ui.fragment.home

import com.airbnb.epoxy.TypedEpoxyController
import com.example.tracker_android.ui.fragment.home.model.*
import com.example.tracker_android.ui.fragment.home.model.HomeItem.*
import vn.teko.android.tracker.core.TrackingInterface

class HomeController(private val tracking: TrackingInterface) :
    TypedEpoxyController<List<HomeItem>>() {

    var callback: Callback? = null

    override fun buildModels(data: List<HomeItem>?) {
        data?.mapIndexed { _, item ->
            when (item) {
                is Button -> {
                    ButtonItemModel_()
                        .id(item.id)
                        .title(item.title)
                        .clickListener { _ ->
                            callback?.onButtonClicked(item)
                        }
                        .addTo(this)
                }
                is HorizontalList -> {
                    HorizontalListModelGroup(
                        tracking,
                        item,
                        object : HorizontalListModelGroup.Callbacks {
                            override fun onHorizontalListItemClick(item: ListItem) {
                                callback?.onHorizontalListItemClick(item)
                            }
                        })
                        .id(item.id)
                        .addTo(this)
                }
                is SingleItem -> {
                    SingleItemModel_()
                        .id(item.id)
                        .title(item.name)
                        .addTo(this)
                }
            }
        }
    }

    interface Callback {
        fun onButtonClicked(button: Button)
        fun onHorizontalListItemClick(item: ListItem)
    }

}