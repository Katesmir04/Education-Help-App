package com.kate.app.educationhelp.presentation.alltopics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kate.app.educationhelp.databinding.FragmentAllTopicsBinding

class AllTopicsFragment : Fragment() {

    private val binding: FragmentAllTopicsBinding by lazy {
        FragmentAllTopicsBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<AllTopicsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AllTopicsAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.topicsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                TopicsListState.Loading -> {
                    binding.root.isRefreshing = true
                }
                is TopicsListState.Loaded -> {
                    binding.root.isRefreshing = false
                    adapter.submitList(state.content)
                }
            }

        }

        binding.root.setOnRefreshListener {
            viewModel.refreshData()
        }
    }
}