package com.kate.app.educationhelp.presentation.quize

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.QuizeResultsItemBinding
import com.kate.app.educationhelp.presentation.quize.QuizeFragment.QuizeResults

class QuizeResultsAdapter(private val context: Context) :
    ListAdapter<QuizeResults, QuizeResultsAdapter.Holder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(private val binding: QuizeResultsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            QuizeResultsItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

        fun bind(quizeResults: QuizeResults) {
            binding.apply {
                name.text = quizeResults.test.title
                when (quizeResults.correct) {
                    true -> {
                        correctness.text = "Correct"
                        correctness.setTextColor(context.resources.getColor(R.color.green))
                    }
                    else -> {
                        correctness.text = "Incorrect"
                        correctness.setTextColor(context.resources.getColor(R.color.red))
                    }
                }

            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<QuizeResults>() {
        override fun areItemsTheSame(oldItem: QuizeResults, newItem: QuizeResults): Boolean {
            return oldItem.test.id == newItem.test.id
        }

        override fun areContentsTheSame(oldItem: QuizeResults, newItem: QuizeResults): Boolean {
            return oldItem == newItem
        }
    }
}