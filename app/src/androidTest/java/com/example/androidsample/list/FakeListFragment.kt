package com.example.androidsample.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.androidsample.ui.list.ListFragment
import com.example.androidsample.ui.list.ListViewModel

internal class ListFragmentFactory(
    private val fakeViewModel: ListViewModel
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            ListFragment::class.java -> FakeListFragment(
                fakeViewModel = fakeViewModel,
            )
            else -> super.instantiate(classLoader, className)
        }
}

internal class FakeListFragment(
    private val fakeViewModel: ListViewModel
) : ListFragment() {
    override val viewModel = fakeViewModel
}
