package com.kate.app.educationhelp.presentation.alltopics

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.TopicItemBinding
import com.kate.app.educationhelp.domain.models.Topic

class AllTopicsAdapter(
    private val context: Context,
    private val topicClick: (topic: Topic) -> Unit
) :
    ListAdapter<Topic, AllTopicsAdapter.Holder>(DiffCallback) {
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

        fun bind(topic: Topic) {
            binding.apply {
                title.text = topic.title
                subjectAndGrade.text = context.resources.getString(
                    R.string.subject_and_grade,
                    topic.subject,
                    topic.grade.toString()
                )
                body.text = topic.body?.run {
                    replace("//n", "").run {
                        replace("//t", "")
                    }
                }
                wholeCard.setOnClickListener {
                    topicClick.invoke(topic)
                }
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