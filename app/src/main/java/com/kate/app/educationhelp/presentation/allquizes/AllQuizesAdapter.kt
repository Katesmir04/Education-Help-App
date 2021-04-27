package com.kate.app.educationhelp.presentation.allquizes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kate.app.educationhelp.databinding.TopicItemBinding
import com.kate.app.educationhelp.domain.models.Quize

class AllQuizesAdapter(private val quizeClick: (quize: Quize) -> Unit) :
    ListAdapter<Quize, AllQuizesAdapter.Holder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(private val binding: TopicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            TopicItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

        fun bind(quize: Quize) {
            binding.apply {
                title.text = (adapterPosition + 1).toString()
                // bodyTV.text = quize.title

                wholeCard.setOnClickListener {
                    quizeClick.invoke(quize)
                }
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Quize>() {
        override fun areItemsTheSame(oldItem: Quize, newItem: Quize): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quize, newItem: Quize): Boolean {
            return oldItem == newItem
        }
    }
}