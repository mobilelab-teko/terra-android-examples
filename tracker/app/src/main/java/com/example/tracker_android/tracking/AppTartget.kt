package com.example.tracker_android.tracking

import vn.teko.android.tracker.event.body.InteractionContentEventBody

enum class AppTartget(override val value: String) : InteractionContentEventBody.Target {
    Target1("target1"),
    Target2("target2")
}