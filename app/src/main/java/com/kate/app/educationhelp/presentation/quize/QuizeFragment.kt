package com.kate.app.educationhelp.presentation.quize

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kate.app.educationhelp.databinding.FragmentQuizeBinding

class QuizeFragment : Fragment() {

    private val binding: FragmentQuizeBinding by lazy {
        FragmentQuizeBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<QuizeViewModel>()

    private val topicId by lazy {
        QuizeFragmentArgs.fromBundle(requireArguments()).topicId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.testsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                TestsListState.Loading -> {

                }
                is TestsListState.Loaded -> {
                    Log.d("TESTS", "tests ${state.content}")

                    binding.pager.apply {
                        adapter = ViewPagerTestAdapter(
                            requireContext(),
                            state.content
                        ) {
                            //move to next


                        }
                        clipToPadding = false;
                        setPadding(60, 60, 60, 60);
                        pageMargin = 16
                    }
                }
            }
        }

        viewModel.refreshData(topicId)
    }
}