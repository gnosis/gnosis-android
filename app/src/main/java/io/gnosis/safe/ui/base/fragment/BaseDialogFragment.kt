package io.gnosis.safe.ui.base.fragment

import android.content.Context
import androidx.fragment.app.DialogFragment
import io.gnosis.safe.HeimdallApplication
import io.gnosis.safe.ScreenId
import io.gnosis.safe.Tracker
import io.gnosis.safe.di.components.DaggerViewComponent
import io.gnosis.safe.di.components.ViewComponent
import io.gnosis.safe.di.modules.ViewModule
import javax.inject.Inject

abstract class BaseDialogFragment : DialogFragment() {

    @Inject
    lateinit var tracker: Tracker

    override fun onAttach(context: Context) {
        super.onAttach(context)
        HeimdallApplication[requireContext()].inject(this)
    }

    override fun onResume() {
        super.onResume()
        screenId()?.let {
            tracker.logScreen(it)
        }
    }

    protected fun buildViewComponent(context: Context): ViewComponent =
        DaggerViewComponent.builder()
            .applicationComponent(HeimdallApplication[context])
            .viewModule(ViewModule(context, viewModelProvider()))
            .build()

    abstract fun screenId(): ScreenId?

    abstract fun inject(component: ViewComponent)

    protected open fun viewModelProvider(): Any? = parentFragment
}
