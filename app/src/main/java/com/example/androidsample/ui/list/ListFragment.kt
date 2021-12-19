package com.example.androidsample.ui.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidsample.R
import com.example.androidsample.databinding.FragmentListBinding
import com.example.androidsample.extensions.viewBinding
import com.example.androidsample.model.Post
import com.example.androidsample.ui.list.ListEffect.HandleThrowable
import com.example.androidsample.ui.list.ListEffect.LaunchDetailsScreen
import com.example.androidsample.ui.list.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {
    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel by viewModels<ListViewModel>()
    private val postAdapter by lazy {
        PostAdapter(
            mutableListOf(),
            viewModel::onItemClicked
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        observeState()
        observeEffects()

        viewModel.refresh()
    }

    private fun setup() {
        binding.rvPost.adapter = postAdapter
        binding.swipeRefresh.setOnRefreshListener(viewModel::refresh)
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                updateUi(state)
            }
        }
    }

    private fun updateUi(state: ListState) {
        binding.swipeRefresh.isRefreshing = state.isLoading
        postAdapter.updateList(state.posts)
    }

    private fun observeEffects() {
        lifecycleScope.launchWhenStarted {
            viewModel.effects.collectLatest { effect ->
                when (effect) {
                    is LaunchDetailsScreen -> launchDetailsScreen(effect.post)
                    is HandleThrowable -> handleThrowable(effect.throwable)
                }
            }
        }
    }

    private fun launchDetailsScreen(post: Post) {
        // TODO: pass post here
        findNavController().navigate(R.id.viewDetails)
    }

    private fun handleThrowable(throwable: Throwable) {
        Toast.makeText(requireContext(), "Error occurred: ${throwable.message}", LENGTH_LONG).show()
    }
}
