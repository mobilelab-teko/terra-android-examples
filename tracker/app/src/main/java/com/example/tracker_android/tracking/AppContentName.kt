package com.example.tracker_android.tracking

import vn.teko.android.tracker.event.body.InteractionContentEventBody

enum class AppContentName(override val value: String) :
    InteractionContentEventBody.ContentName {
    ContentName1("content_name_1"),
    ContentName2("content_name_2")
}