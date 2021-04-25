package com.kate.app.educationhelp.presentation.quize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kate.app.educationhelp.databinding.FragmentQuizeResultsBinding
import com.kate.app.educationhelp.presentation.quize.QuizeFragment.QuizeResults

class QuizeResultsFragment : Fragment() {

    private val binding: FragmentQuizeResultsBinding by lazy {
        FragmentQuizeResultsBinding.inflate(layoutInflater)
    }

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
        binding.recycler.adapter = adapter
        adapter.submitList(results)

        binding.confirm.setOnClickListener {

        }
    }
}