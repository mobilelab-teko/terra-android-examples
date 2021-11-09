package com.example.tracker_android.ui.fragment.home.model

sealed class HomeItem(open val id: String) {

    data class Button(override val id: String, val title: String) : HomeItem(id)

    data class HorizontalList(
        override val id: String,
        val title: String,
        val items: List<ListItem>
    ) : HomeItem(id)

    data class SingleItem(override val id: String, val name: String, val span: Int) : HomeItem(id)

}