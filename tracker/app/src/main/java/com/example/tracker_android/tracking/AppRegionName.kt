package com.example.tracker_android.tracking

import vn.teko.android.tracker.event.body.InteractionContentEventBody

enum class AppRegionName(override val value: String) :
    InteractionContentEventBody.RegionName {
    List1("list1"),
    List2("list2")
}