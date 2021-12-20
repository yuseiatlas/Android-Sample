package com.example.androidsample.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.androidsample.ui.details.DetailsFragment
import com.example.androidsample.ui.details.DetailsViewModel

internal class DetailsFragmentFactory(
    private val fakeViewModel: DetailsViewModel
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            DetailsFragment::class.java -> FakeDetailsFragment(
                fakeViewModel = fakeViewModel,
            )
            else -> super.instantiate(classLoader, className)
        }
}

internal class FakeDetailsFragment(
    private val fakeViewModel: DetailsViewModel
) : DetailsFragment() {
    override val viewModel = fakeViewModel
}
