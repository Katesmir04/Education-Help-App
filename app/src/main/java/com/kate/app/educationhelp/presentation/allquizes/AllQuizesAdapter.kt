package com.kate.app.educationhelp.presentation.allquizes

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

class AllQuizesAdapter(
    private val context: Context,
    private val quizeClick: (quize: Quize) -> Unit,
    private val showRetakeDialog: (quize: Quize, bonuses: String, completed: String) -> Unit
) :
    ListAdapter<Quize, AllQuizesAdapter.Holder>(DiffCallback) {

    private var listOfPassedQuizes = listOf<QuizeItem>()

    fun putPassedQuizes(list: List<QuizeItem>) {
        listOfPassedQuizes = list
    }

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

        fun bind(quize: Quize) {
            binding.apply {

                val occurs = listOfPassedQuizes.filter {
                    it.quize.id == quize.id
                }


                if (occurs.isNotEmpty()) {
                    passedBonuses.visibility = View.VISIBLE
                    val bonuses = "Бонусов получено: ${occurs.first().bonuses}"
                    passedBonuses.text = bonuses

                    passedCount.visibility = View.VISIBLE
                    val completedSize = getCompletedSize(occurs)
                    val completed = "Выполнено: ${
                        completedSize
                    }/${occurs.first().results.size}"
                    passedCount.text = completed


                    setCompletedCardColor(
                        completedSize, quize, bonuses,
                        completed,
                    )

                } else {
                    wholeCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    passedBonuses.visibility = View.GONE
                    passedCount.visibility = View.GONE

                    wholeCard.setOnClickListener {
                        quizeClick.invoke(quize)
                    }
                }

                title.text = quize.title
                body.text = context.resources.getString(
                    R.string.subject_and_grade,
                    quize.subject,
                    quize.grade.toString()
                )
                // bodyTV.text = quize.title


            }
        }
    }

    private fun getCompletedSize(occurs: List<QuizeItem>) =
        occurs.first().results.filter {
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

    object DiffCallback : DiffUtil.ItemCallback<Quize>() {
        override fun areItemsTheSame(oldItem: Quize, newItem: Quize): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quize, newItem: Quize): Boolean {
            return oldItem == newItem
        }
    }
}