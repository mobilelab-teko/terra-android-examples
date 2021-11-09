package com.example.tracker_android.ui.fragment.home.model

sealed class ItemType {

    sealed class ClickToNavigate : ItemType() {
        object NavigateTabFragment : ClickToNavigate()
        object NavigateViewPager : ClickToNavigate()
        object NavigateSecondaryActivity : ClickToNavigate()
        object NavigateUntrackedFragment : ClickToNavigate()
    }

    sealed class ClickToTrack : ItemType() {
        object TrackAlert : ClickToTrack()
        object FireCrash : ClickToTrack()
        object TrackCart : ClickToTrack()
        object TrackCheckout : ClickToTrack()
        object TrackError : ClickToTrack()
        object TrackPayment : ClickToTrack()
        object TrackSiteSearch : ClickToTrack()
        object TrackVisibleContent : ClickToTrack()
        object TrackInteraction : ClickToTrack()
        object TrackScreenView : ClickToTrack()
        object TrackCustom : ClickToTrack()
    }

    sealed class ClickToDoOther : ItemType() {
        object OpenBottomSheet : ClickToDoOther()
        object OpenFakeBottomSheet : ClickToDoOther()
    }
}