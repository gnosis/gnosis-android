package pm.gnosis.heimdall.common.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pm.gnosis.heimdall.common.di.ViewModelKey
import pm.gnosis.heimdall.ui.base.BaseContract
import pm.gnosis.heimdall.ui.security.SecurityContract
import pm.gnosis.heimdall.ui.security.SecurityViewModel
import javax.inject.Singleton

@Module
abstract class ViewModelBindingsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SecurityContract.ViewModel::class)
    abstract fun bindsSecurityViewModel(viewModel: SecurityViewModel): ViewModel

    @Binds
    @Singleton
    abstract fun bindsViewModelFactory(viewModel: BaseContract.ViewModelFactory): ViewModelProvider.Factory
}