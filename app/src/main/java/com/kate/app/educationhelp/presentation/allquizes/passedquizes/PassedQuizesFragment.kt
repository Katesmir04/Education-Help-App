package com.kate.app.educationhelp.presentation.allquizes.passedquizes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentPassedQuizesBinding
import com.kate.app.educationhelp.presentation.allquizes.RetakeFragmentArgs


class PassedQuizesFragment : Fragment() {

    private val binding by lazy {
        FragmentPassedQuizesBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<PassedQuizesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PassedQuizesAdapter(requireContext(),
            showRetakeDialog = { quize, bonuses, completed ->
                findNavController().navigate(
                    R.id.action_passedQuizesFragment_to_retakeFragment, RetakeFragmentArgs(
                        quize = quize,
                        bonuses, completed
                    ).toBundle()
                )

            })
        binding.recyclerView.adapter = adapter
        viewModel.quizesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                PassedQuizesListState.Loading -> {
                    binding.root.isRefreshing = true
                }
                is PassedQuizesListState.Loaded -> {
                    binding.root.isRefreshing = false
                    adapter.submitList(state.passedQuizes)
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

}