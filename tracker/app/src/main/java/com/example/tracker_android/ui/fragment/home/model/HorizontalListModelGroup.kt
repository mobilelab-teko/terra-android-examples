package com.example.tracker_android.ui.fragment.home.model

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.example.tracker_android.R
import com.example.tracker_android.tracking.AppRegionName.List1
import com.example.tracker_android.ui.fragment.home.model.HomeItem.HorizontalList
import vn.teko.android.tracker.core.Constants
import vn.teko.android.tracker.core.TrackingInterface
import vn.teko.android.tracker.core.util.RecyclerViewTrackingListener

class HorizontalListModelGroup(
    tracking: TrackingInterface,
    horizontalList: HorizontalList,
    callbacks: Callbacks
) :
    EpoxyModelGroup(
        R.layout.layout_horizontal_list,
        buildModels(tracking, horizontalList, callbacks)
    ) {

    interface Callbacks {
        fun onHorizontalListItemClick(item: ListItem)
    }

    companion object {

        fun buildModels(
            tracking: TrackingInterface,
            horizontalList: HorizontalList,
            callbacks: Callbacks
        ): List<EpoxyModel<*>> {
            val result = mutableListOf<EpoxyModel<*>>()

            result.add(
                TitleItemModel_()
                    .id("title${horizontalList.id}")
                    .title(horizontalList.title)
            )

            val models = horizontalList.items.mapIndexed { idx, item ->
                ButtonItemModel_()
                    .id("list_item_$idx")
                    .title(item.name)
                    .clickListener(View.OnClickListener { callbacks.onHorizontalListItemClick(item) })
            }

            result.add(
                GridCarouselModel_()
                    .id(horizontalList.id)
                    .models(models)
                    .spanCount(1)
                    .padding(Carousel.Padding.dp(0, 0, 0, 0, 0))
                    .orientation(GridLayoutManager.HORIZONTAL)
                    .onScrollListener(
                        RecyclerViewTrackingListener(
                            tracking,
                            List1,
                            object : RecyclerViewTrackingListener.ItemTrackingInfoExtractor {
                                override fun getContentName(position: Int): String {
                                    return if (position >= 0 && position < models.size) {
                                        models[position].title() ?: Constants.UNKNOWN_VALUE
                                    } else {
                                        ""
                                    }
                                }
                            })
                    )
            )

            return result
        }
    }

}