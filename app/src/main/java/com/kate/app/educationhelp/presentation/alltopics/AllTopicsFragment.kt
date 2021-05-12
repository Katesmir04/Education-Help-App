package com.kate.app.educationhelp.presentation.alltopics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentAllTopicsBinding
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.presentation.topicdescr.TopicDescriptionFragmentArgs

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

        val adapter = AllTopicsAdapter(requireContext()) {
            onTopicClicked(it)
        }
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

        var lastCheckedId = View.NO_ID

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == View.NO_ID) {
                group.check(lastCheckedId)
                return@setOnCheckedChangeListener
            }

            lastCheckedId = checkedId

            adapter.submitList(viewModel.filterListByName(binding.root.findViewById<Chip>(checkedId).text.toString()))
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

    }

    private fun onTopicClicked(topic: Topic) {
        val args = TopicDescriptionFragmentArgs(topic = topic).toBundle()
        findNavController().navigate(
            R.id.action_allTopicsFragment_to_topicDescriptionFragment,
            args
        )
    }
}