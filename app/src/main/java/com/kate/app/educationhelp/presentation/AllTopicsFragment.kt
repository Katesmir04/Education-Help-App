package com.kate.app.educationhelp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentAllTopicsBinding
import com.kate.app.educationhelp.databinding.ItemHolderBinding
import com.kate.app.educationhelp.domain.models.Topic

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

        val adapter = Adapter()
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


    class Adapter : ListAdapter<Topic, Adapter.Holder>(DiffCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(parent)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(getItem(position))
        }

        class Holder(private val binding: ItemHolderBinding) :
            RecyclerView.ViewHolder(binding.root) {
            constructor(parent: ViewGroup) : this(
                ItemHolderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            fun bind(topic: Topic) {
                binding.apply {
                    titleTV.text = topic.title
                    bodyTV.text = topic.body
                }
            }
        }

        object DiffCallback : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
                return oldItem == newItem
            }
        }
    }
}