package com.example.tracker_android.ui.fragment.home

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.tracker_android.BuildConfig
import com.example.tracker_android.R
import com.example.tracker_android.databinding.FragmentHomeBinding
import com.example.tracker_android.tracking.*
import com.example.tracker_android.ui.activity.SecondaryActivity
import com.example.tracker_android.ui.fragment.BaseTrackableFragment
import com.example.tracker_android.ui.fragment.home.model.HomeItem
import com.example.tracker_android.ui.fragment.home.model.ItemType
import com.example.tracker_android.ui.fragment.home.model.ListItem
import vn.teko.android.tracker.core.TrackingInterface
import vn.teko.android.tracker.event.CartEventIdentifier
import vn.teko.android.tracker.event.EventConstants
import vn.teko.android.tracker.event.ScreenViewEventIdentifier
import vn.teko.android.tracker.event.body.*
import vn.teko.android.tracker.manager.TerraTracker
import vn.teko.terra.core.android.terra.TerraApp


class HomeFragment : BaseTrackableFragment<FragmentHomeBinding>(), HomeController.Callback {

    private lateinit var homeController: HomeController

    private lateinit var terraApp: TerraApp

    private lateinit var tracking: TrackingInterface

    override val layoutId: Int = R.layout.fragment_home

    override fun initViews() {
        initTerraApp()
        initTracker()

        initListView()
    }

    private fun initListView() {
        viewDataBinding?.items?.setController(homeController)
        homeController.callback = this

        homeController.setData(
            listOf(
                HomeItem.HorizontalList(
                    id = "navigation", title = "Navigation", items = listOf(
                        ListItem(
                            ItemType.ClickToNavigate.NavigateTabFragment,
                            "Navigate Tab Fragment"
                        ),
                        ListItem(ItemType.ClickToNavigate.NavigateViewPager, "Navigate View Pager"),
                        ListItem(
                            ItemType.ClickToNavigate.NavigateUntrackedFragment,
                            "Navigate Untracked Fragment"
                        )
                    )
                ),
                HomeItem.HorizontalList(
                    id = "navigation", title = "Navigation", items = listOf(
                        ListItem(
                            ItemType.ClickToNavigate.NavigateSecondaryActivity,
                            "Navigate Secondary Activity"
                        )
                    )
                ),
                HomeItem.HorizontalList(
                    id = "navigation", title = "Navigation", items = listOf(
                        ListItem(
                            ItemType.ClickToDoOther.OpenBottomSheet,
                            "Open BottomSheet"
                        ),
                        ListItem(
                            ItemType.ClickToDoOther.OpenFakeBottomSheet,
                            "Open FakeBottomSheet"
                        )
                    )
                ),
                HomeItem.HorizontalList(
                    id = "alert_event", title = "Track Alert Event", items = listOf(
                        ListItem(ItemType.ClickToTrack.TrackAlert, "Click Alert")
                    )
                ),
                HomeItem.HorizontalList(
                    id = "exception_event", title = "Fire Crash Event", items = listOf(
                        ListItem(ItemType.ClickToTrack.FireCrash, "Fire Crash")
                    )
                ),
                HomeItem.HorizontalList(
                    id = "cart_event", title = "Track Cart Event", items = listOf(
                        ListItem(ItemType.ClickToTrack.TrackCart, "Add to cart")
                    )
                ),
                HomeItem.HorizontalList(
                    id = "search_event", title = "Track Search Event", items = listOf(
                        ListItem(ItemType.ClickToTrack.TrackSiteSearch, "Search")
                    )
                ),
                HomeItem.HorizontalList(
                    id = "checkout_event", title = "Track Checkout Event", items = listOf(
                        ListItem(ItemType.ClickToTrack.TrackCheckout, "Checkout")
                    )
                ),
                HomeItem.HorizontalList(
                    id = "payment_event", title = "Track Payment Event", items = listOf(
                        ListItem(ItemType.ClickToTrack.TrackPayment, "Payment")
                    )
                ),
                HomeItem.HorizontalList(
                    id = "interaction_event", title = "Track Interaction Event", items = listOf(
                        ListItem(ItemType.ClickToTrack.TrackInteraction, "Interaction")
                    )
                ),
                HomeItem.HorizontalList(
                    id = "error_event", title = "Track Error Event", items = listOf(
                        ListItem(ItemType.ClickToTrack.TrackError, "Error")
                    )
                ),
                HomeItem.HorizontalList(
                    id = "custom_event", title = "Track Custom Event", items = listOf(
                        ListItem(ItemType.ClickToTrack.TrackCustom, "Custom")
                    )
                ),
                HomeItem.HorizontalList(
                    id = "visible_content_event",
                    title = "Track Visible Content Event",
                    items = (1..100).map {
                        ListItem(ItemType.ClickToTrack.TrackVisibleContent, "item_$it")
                    })
            )
        )
    }

    private fun initTracker() {
        tracking = TerraTracker.getInstance(terraApp)
        homeController = HomeController(tracking)

        tracking.setUserId("test-user")
        tracking.setPhoneNumber("example-phone")
    }

    private fun initTerraApp() {
        terraApp = TerraApp.getInstance(BuildConfig.APPLICATION_ID)
    }

    override fun onButtonClicked(button: HomeItem.Button) {

    }

    override fun onHorizontalListItemClick(item: ListItem) {
        when (item.itemType) {
            ItemType.ClickToTrack.TrackAlert -> trackAlert()
            ItemType.ClickToTrack.FireCrash -> {
                // simulate crash
                Thread(Runnable { throw Exception("app crash") }).start()

            }
            ItemType.ClickToTrack.TrackCart -> trackAddToCart()
            ItemType.ClickToTrack.TrackCheckout -> trackCheckout()
            ItemType.ClickToTrack.TrackError -> trackError()
            ItemType.ClickToTrack.TrackInteraction -> trackInteraction()
            ItemType.ClickToTrack.TrackPayment -> trackPayment()
            ItemType.ClickToTrack.TrackSiteSearch -> trackSearch()
            ItemType.ClickToTrack.TrackCustom -> trackCustomEvent()
            ItemType.ClickToNavigate.NavigateTabFragment -> navigateTabFragment()
            ItemType.ClickToNavigate.NavigateViewPager -> navigateViewPager()
            ItemType.ClickToNavigate.NavigateUntrackedFragment -> navigateUntrackedFragment()
            ItemType.ClickToNavigate.NavigateSecondaryActivity -> navigateSecondaryActivity()
            ItemType.ClickToDoOther.OpenBottomSheet -> openBottomSheet()
            ItemType.ClickToDoOther.OpenFakeBottomSheet -> openFakeBottomSheet()
            else -> {

            }
        }
    }

    override fun onDestroy() {
        viewDataBinding = null
        viewDataBinding?.items?.clear()
        super.onDestroy()
    }

    enum class ButtonId(val id: String) {
        AddToCart("AddToCart"),
        Alert("Alert")
    }

    private fun trackAlert() {
        tracking.trackAlertEvent(
            AlertEventBody(
                alertType = AlertEventBody.AlertType.Popup,
                alertMessage = "this is an alert"
            )
        )
    }

    private fun trackAddToCart() {
        tracking.trackCartEvent(
            body = CartEventBody(
                skuId = "skuId",
                skuName = "skuName",
                price = 100000,
                promotionPrice = 1000,
                quantity = 3,
                status = EventConstants.Status.Success,
                promotionId = "promotionId",
                coupon = "coupon",
                cartId = "cartId",
                cartEventName = CartEventIdentifier.CartEventName.AddToCart
            )
        )
    }

    private fun trackSearch() {
        tracking.trackSiteSearchEvent(
            SiteSearchEventBody(
                params = "params",
                keywords = "keywords",
                sort = "latest",
                order = "asc"
            )
        )
    }

    private fun trackInteraction() {
        tracking.trackInteraction(
            InteractionContentEventBody(
                regionName = AppRegionName.List1,
                contentName = AppContentName.ContentName1,
                target = AppTartget.Target1,
                payload = "sample payload",
                interaction = InteractionContentEventBody.Interaction.Click
            )
        )
    }

    private fun trackPayment() {
        tracking.trackPaymentEvent(
            PaymentEventBody(
                orderId = "orderId",
                amount = 100000,
                paymentMethod = AppMethodCode.VNPAY,
                paymentBank = "Vietcombank",
                status = EventConstants.Status.Success.value,
                statusCode = 200,
                referral = "referral"
            )
        )
    }

    private fun trackError() {
        tracking.trackErrorEvent(
            ErrorEventBody(
                errorSource = ErrorEventBody.ErrorSource.Http,
                apiCall = "sample api",
                apiPayload = "sample payload",
                httpResponseCode = "httpResponseCode",
                responseJson = "responseJson",
                errorCode = "errorCode",
                errorMessage = "errorMessage",
                sdkVersion = "sdk version demo",
                sdkId = "sdk id demo"
            )
        )
    }

    private fun trackCheckout() {
        tracking.trackCheckoutEvent(
            CheckoutEventBody(
                amountAfterDiscount = 100000,
                amountBeforeDiscount = 100000,
                products = listOf(
                    CheckoutEventBody.Product(
                        skuId = "skuId1",
                        skuName = "skuName1",
                        price = 1000000,
                        promotionPrice = 1000,
                        quantity = 2,
                        cartId = "cartId1"
                    )
                ),
                status = EventConstants.Status.Success,
                paymentMethod = "VNPAYQR",
                orderId = "orderId",
                discountAmount = 1000,
                shippingFee = 15000,
                shippingAddressCode = "shippingAddressCode",
                shippingProvince = "shippingProvince",
                shippingDistrict = "shippingDistrict",
                shippingAddress = "shippingAddress",
                shippingWard = "shippingWard",
                shippingStreet = "shippingStreet",
                note = "note"
            )
        )
    }

    private fun trackCustomEvent() {
        tracking.trackCustomEvent(
            CustomEventBody(
                utmSource = "utmSource",
                utmTerm = "utmTerm",
                utmCampaign = "utmCampaign",
                utmMedium = "utmMedium",
                utmContent = "utmContent",
                category = "category",
                action = "action",
                label = "label",
                property = "properties",
                value = 99
            )
        )
    }

    private fun navigateTabFragment() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTabNavigationFragment())
    }

    private fun navigateViewPager() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToViewPagerFragment())
    }

    private fun navigateUntrackedFragment() {
        findNavController().navigate(HomeFragmentDirections.actionHomeToUntracked())
    }

    private fun navigateSecondaryActivity() {
        startActivity(Intent(context, SecondaryActivity::class.java))
    }

    private fun openBottomSheet() {
        val popup = DemoBottomSheet()
        popup.show(childFragmentManager, "DemoBottomSheet")
    }

    private fun openFakeBottomSheet() {
        // sometime app uses custom view to make popup, so the lib can not track this.
        // you will have to do yourself
        tracking.trackScreenViewEvent(
            ScreenViewEventBody(
                screenViewEventName = ScreenViewEventIdentifier.ScreenViewEventName.EnterScreenView,
                screenName = AppScreenName.FakeScreen,
                contentType = AppContentType.ContentType1
            )
        )

        AlertDialog.Builder(requireContext())
            .setTitle("Fake Screen")
            .setMessage("This is a fake screen (not fragment/activity) but app still want to track it as a screen") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which ->
                tracking.trackScreenViewEvent(
                    ScreenViewEventBody(
                        screenViewEventName = ScreenViewEventIdentifier.ScreenViewEventName.ExitScreenView,
                        screenName = AppScreenName.FakeScreen,
                        contentType = AppContentType.ContentType1,
                    )
                )
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setCancelable(false)
            .show()
    }

    override fun screenName(): ScreenViewEventBody.ScreenName = AppScreenName.Home

    override fun contentType(): ScreenViewEventBody.ContentType = AppContentType.ContentType2

}