package com.example.tracker_android.tracking

import vn.teko.android.tracker.event.body.PaymentEventBody


/**
 * Created by TrungCS on 10/06/2021.
 * Email: trung.cs@teko.vn
 * Company: Teko
 */
enum class AppMethodCode(override val value: String): PaymentEventBody.MethodCode {
    VNPAY("VnPayQR")
}