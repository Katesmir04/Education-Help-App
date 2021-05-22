package com.kate.app.educationhelp.presentation.quize

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.shape.CornerFamily
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentQuizeResultsBinding
import com.kate.app.educationhelp.presentation.quize.QuizeFragment.QuizeResults
import com.kate.app.educationhelp.presentation.topicdescr.TopicDescriptionFragmentArgs


class QuizeResultsFragment : Fragment() {

    private val binding: FragmentQuizeResultsBinding by lazy {
        FragmentQuizeResultsBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<QuizeResultsViewModel>()

    private lateinit var results: List<QuizeResults>

    private val startFragment by lazy {
        QuizeResultsFragmentArgs.fromBundle(requireArguments()).startFragment
    }

    private val topic by lazy {
        QuizeResultsFragmentArgs.fromBundle(requireArguments()).topic
    }

    private val quize by lazy {
        QuizeResultsFragmentArgs.fromBundle(requireArguments()).quize
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radius = 32f
        binding.image.shapeAppearanceModel = binding.image.shapeAppearanceModel
            .toBuilder()
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .build()

        results = QuizeResultsFragmentArgs.fromBundle(requireArguments()).results.toList()
        val adapter = QuizeResultsAdapter(requireContext())

        binding.recycler.adapter = adapter
        adapter.submitList(results)

        binding.confirm.setOnClickListener {
            viewModel.updateQuizeStatus(results.totalBonuses(), quize = quize, results = results) {
                Log.d("KEK", "updated")
                if (startFragment == R.id.topicDescriptionFragment) {
                    findNavController().navigate(R.id.action_quizeResultsFragment_to_topicDescriptionFragment,
                        topic?.let { it1 -> TopicDescriptionFragmentArgs(it1).toBundle() })
                } else if (startFragment == R.id.allQuizesFragment) {
                    findNavController().navigate(R.id.action_quizeResultsFragment_to_allQuizesFragment)
                }
            }
        }

        binding.bonusesProgress.max = results.size

        binding.bonusesFrom.text = "${
            results.filter {
                it.correct
            }.size
        } из ${results.size}"

        binding.bonusesProgress.progress = results.filter {
            it.correct
        }.size

        binding.totalB.text = "${results.totalBonuses()} б"

        binding.bonusesSum.text =
            "${((binding.bonusesProgress.progress.toDouble() / binding.bonusesProgress.max.toDouble()) * 100).toInt()} %"
    }


    private fun List<QuizeResults>.totalBonuses() = filter {
        it.correct
    }.sumBy {
        it.bonus
    }
}