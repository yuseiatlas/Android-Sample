package com.example.androidsample.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.androidsample.R
import com.example.androidsample.databinding.FragmentDetailsBinding
import com.example.androidsample.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
open class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val args by navArgs<DetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: DetailsViewModel.DetailsViewModelFactory

    protected open val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.provideFactory(viewModelFactory, args.postId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    updateUi(state)
                }
            }
        }
    }

    private fun updateUi(state: DetailsState) {
        state.post?.let { post ->
            binding.tvTitle.text = post.title
            binding.tvAuthor.text = getString(R.string.details_author_title, post.author, post.username)
            binding.tvBody.text = post.body
        }
    }
}
