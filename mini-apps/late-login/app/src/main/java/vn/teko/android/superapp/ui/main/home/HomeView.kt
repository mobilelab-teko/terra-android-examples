package vn.teko.android.superapp.ui.main.home

import vn.teko.android.core.ui.base.BaseView


interface HomeView : BaseView {
    fun goToLogin()
    fun openMiniApp()
    fun showError(e: Throwable)
    fun toggleLoading(show: Boolean)
}
