package com.kate.app.educationhelp.presentation.quize

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentQuizeBinding
import com.kate.app.educationhelp.domain.models.Test
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizeFragment : Fragment() {

    private val binding: FragmentQuizeBinding by lazy {
        FragmentQuizeBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<QuizeViewModel>()

    private val topicId by lazy {
        QuizeFragmentArgs.fromBundle(requireArguments()).topicId
    }

    private var answers: MutableList<QuizeResults> = mutableListOf()
    private var goToResults = false
    private var bonusesProgress: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bonusesSum.text = "Удачи!"

        viewModel.testsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                TestsListState.Loading -> {

                }
                is TestsListState.Loaded -> {
                    binding.pager.apply {
                        adapter = ViewPagerTestAdapter(
                            requireContext(),
                            state.content,
                            confirm = {
                                answers.add(QuizeResults(it.first, it.second, it.third))

                                if (it.second) {
                                    bonusesProgress += it.third
                                }

                                updateProgress(state.content)
                            },
                            next = {
                                GlobalScope.launch(Dispatchers.Main) {
                                    delay(1000)
                                    moveToNextItem(state.content)
                                }

                            })
//                        clipToPadding = false;
//                        setPadding(60, 60, 60, 60);
//                        pageMargin = 16
                    }
                }
            }
        }

        topicId.let { viewModel.refreshData(it) }
        //binding.title.text = topicId.title ?: "..."

    }

    private fun updateProgress(tests: List<Test>) {
        binding.bonusesProgress.max =
            tests.mapNotNull { test ->
                test.bonus
            }.sumBy { bonus ->
                bonus
            }

        if(bonusesProgress == 0){
            binding.bonusesSum.text = "Удачи!"
        } else {
            binding.bonusesSum.text = "$bonusesProgress б"
        }
        binding.bonusesProgress.progress =
            bonusesProgress


    }

    private fun moveToNextItem(list: List<Test>) {

        if (goToResults) {
            findNavController().navigate(
                R.id.action_quizeFragment_to_quizeResultsFragment,
                QuizeResultsFragmentArgs(results = answers.toTypedArray()).toBundle()
            )
        }

        var currentPosition = binding.pager.currentItem

        if (currentPosition < list.size - 1) {
            currentPosition++
            binding.pager.currentItem = currentPosition
        }

        if (currentPosition == list.size - 1) {
            goToResults = true
        }


    }

    @Parcelize
    data class QuizeResults(
        val test: Test,
        val correct: Boolean,
        val bonus: Int
    ) : Parcelable
}