package com.kate.app.educationhelp.presentation.allquizes.passedquizes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentAllQuizesItemBinding
import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.presentation.quize.QuizeResultsViewModel.QuizeItem

class PassedQuizesAdapter(
    private val context: Context,
    private val showRetakeDialog: (quize: Quize, bonuses: String, completed: String) -> Unit
) :
    ListAdapter<QuizeItem, PassedQuizesAdapter.Holder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(private val binding: FragmentAllQuizesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            FragmentAllQuizesItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

        fun bind(quizeItem: QuizeItem) {
            binding.apply {

                passedBonuses.visibility = View.VISIBLE
                val bonuses = "Бонусов получено: ${quizeItem.bonuses}"
                passedBonuses.text = bonuses

                passedCount.visibility = View.VISIBLE
                val completedSize = getCompletedSize(quizeItem)
                val completed = "Выполнено: ${
                    completedSize
                }/${quizeItem.results.size}"
                passedCount.text = completed


                setCompletedCardColor(
                    completedSize, quizeItem.quize, bonuses,
                    completed,
                )

                title.text = quizeItem.quize.title
                body.text = context.resources.getString(
                    R.string.subject_and_grade,
                    quizeItem.quize.subject,
                    quizeItem.quize.grade.toString()
                )
            }
        }
    }

    private fun getCompletedSize(quizeItem: QuizeItem) =
        quizeItem.results.filter {
            it.correct
        }.size

    private fun FragmentAllQuizesItemBinding.setCompletedCardColor(
        completedSize: Int,
        quize: Quize,
        bonuses: String,
        completed: String
    ) {
        if (completedSize == 0) {
            wholeCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.light_red
                )
            )

            passedCount.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.red
                )
            )

            wholeCard.setOnClickListener {
                showRetakeDialog.invoke(quize, bonuses, completed)
            }


        } else {
            wholeCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.light_green
                )
            )


            wholeCard.setOnClickListener {
                Toast.makeText(context, R.string.quize_already_done, Toast.LENGTH_LONG).show()
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<QuizeItem>() {
        override fun areItemsTheSame(oldItem: QuizeItem, newItem: QuizeItem): Boolean {
            return oldItem.quize.id == newItem.quize.id
        }

        override fun areContentsTheSame(oldItem: QuizeItem, newItem: QuizeItem): Boolean {
            return oldItem == newItem
        }
    }
}