package com.example.androidsample.ui.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidsample.R
import com.example.androidsample.databinding.FragmentDetailsBinding
import com.example.androidsample.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel by viewModels<DetailsViewModel>()
}
