package com.kate.app.educationhelp.presentation.allquizes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentAllQuizesBinding
import com.kate.app.educationhelp.presentation.quize.QuizeFragmentArgs

class AllQuizesFragment : Fragment() {

    private val binding: FragmentAllQuizesBinding by lazy {
        FragmentAllQuizesBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<AllQuizesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AllQuizesAdapter(requireContext()) {
            findNavController().navigate(R.id.action_allQuizesFragment_to_quizeFragment,
                it.topicId?.let { it1 -> QuizeFragmentArgs(topicId = it1, startFragment = R.id.allQuizesFragment, topic = null, quize = it).toBundle() })
        }
        binding.recyclerView.adapter = adapter
        viewModel.quizesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                QuizesListState.Loading -> {
                    binding.root.isRefreshing = true
                }
                is QuizesListState.Loaded -> {
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