package vn.teko.android.superapp.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import vn.teko.android.superapp.SuperApp
import vn.teko.android.superapp.di.builder.ActivityBuilder
import vn.teko.android.superapp.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        ActivityBuilder::class
    ]
)
interface AppComponent {

    fun inject(core: SuperApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
