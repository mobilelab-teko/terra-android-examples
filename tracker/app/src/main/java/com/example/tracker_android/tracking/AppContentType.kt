package com.example.tracker_android.tracking

import vn.teko.android.tracker.event.body.ScreenViewEventBody

enum class AppContentType(override val value: String) : ScreenViewEventBody.ContentType {
    ContentType1("content_type_1"),
    ContentType2("content_type_2")
}