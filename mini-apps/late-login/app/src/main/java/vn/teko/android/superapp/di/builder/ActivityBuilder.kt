package vn.teko.android.superapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import vn.teko.android.superapp.di.module.FragmentsModule
import vn.teko.android.superapp.ui.loginContainer.LoginContainerActivity
import vn.teko.android.superapp.ui.main.MainActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            FragmentsModule::class
        ]
    )
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            FragmentsModule::class
        ]
    )
    internal abstract fun bindLoginContainerActivity(): LoginContainerActivity

}
