package vn.teko.android.superapp.di.module

import dagger.Module
import vn.teko.android.superapp.ui.main.home.HomeFragmentProvider
import vn.teko.android.superapp.ui.main.login.LoginFragmentProvider

@Module(
    includes = [
        HomeFragmentProvider::class,
        LoginFragmentProvider::class
    ]
)
object FragmentsModule