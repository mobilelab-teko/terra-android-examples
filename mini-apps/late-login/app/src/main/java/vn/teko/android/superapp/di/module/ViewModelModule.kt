package vn.teko.android.superapp.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import vn.teko.android.core.ui.di.viewmodel.ViewModelKey
import vn.teko.android.superapp.ui.loginContainer.LoginContainerViewModel
import vn.teko.android.superapp.ui.main.MainViewModel
import vn.teko.android.superapp.ui.main.home.HomeViewModel
import vn.teko.android.superapp.ui.main.login.LoginViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginContainerViewModel::class)
    internal abstract fun bindLoginContainerViewModel(viewModel: LoginContainerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

}