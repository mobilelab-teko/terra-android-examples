package com.example.tracker_android.tracking

import vn.teko.android.tracker.event.body.ScreenViewEventBody

sealed class AppScreenName(override val value: String) : ScreenViewEventBody.ScreenName {

    object Unknown : AppScreenName("unknown")
    object Home : AppScreenName("home")
    object Tracked : AppScreenName("tracked")
    object DemoBottomSheet : AppScreenName("demoBottomSheet")
    object HomeTab : AppScreenName("homeTab")
    object CategoryTab : AppScreenName("categoryTab")
    object SubCategory : AppScreenName("subCategory")
    object NotificationTab : AppScreenName("notificationTab")
    object AccountTab : AppScreenName("accountTab")
    object TabNavigationContainer : AppScreenName("tabNavigationContainer")
    object ViewPagerContainer : AppScreenName("viewPagerContainer")
    object SecondaryActivity : AppScreenName("secondaryActivity")
    object ThirdActivity : AppScreenName("thirdActivity")
    object FakeScreen : AppScreenName("fakeScreen")

    class ViewPage(name: String) : AppScreenName("viewPage-$name")
}