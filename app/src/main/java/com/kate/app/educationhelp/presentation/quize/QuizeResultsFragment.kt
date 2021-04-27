package com.kate.app.educationhelp.presentation.quize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentQuizeResultsBinding
import com.kate.app.educationhelp.presentation.quize.QuizeFragment.QuizeResults


class QuizeResultsFragment : Fragment() {

    private val binding: FragmentQuizeResultsBinding by lazy {
        FragmentQuizeResultsBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<QuizeResultsViewModel>()

    private lateinit var results: List<QuizeResults>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        results = QuizeResultsFragmentArgs.fromBundle(requireArguments()).results.toList()
        val adapter = QuizeResultsAdapter(requireContext())

        binding.recycler.addItemDecoration(
            DividerItemDecoration(
                binding.recycler.context,
                LinearLayoutManager.VERTICAL
            )
        )

        binding.recycler.adapter = adapter
        adapter.submitList(results)

        binding.confirm.setOnClickListener {
            viewModel.updateBonuses(results.totalBonuses())
            findNavController().navigate(R.id.action_quizeResultsFragment_to_allQuizesFragment)
        }

        binding.totalBonuses.text = resources.getString(
            R.string.total_bonuses,
            results.totalBonuses().toString()
        )
    }


    private fun List<QuizeResults>.totalBonuses() = filter {
        it.correct
    }.sumBy {
        it.bonus
    }
}