package com.kate.app.educationhelp.presentation.feed

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.RatingItemBinding
import com.kate.app.educationhelp.domain.models.User

class RatingAdapter(private val context: Context) :
    ListAdapter<User, RatingAdapter.Holder>(DiffCallback) {

    private var currentUser: User? = null

    fun setUser(user: User) {
        currentUser = user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class Holder(private val binding: RatingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            RatingItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

        fun bind(user: User, pos: Int) {
            binding.apply {

                if (currentUser != null && user.id == currentUser!!.id) {
                    name.setTextColor(ContextCompat.getColor(context, R.color.green))
                    name.setTypeface(null, Typeface.BOLD)
                    position.setTextColor(ContextCompat.getColor(context, R.color.green))
                    position.setTypeface(null, Typeface.BOLD)
                    bonuses.setTextColor(ContextCompat.getColor(context, R.color.green))
                    bonuses.setTypeface(null, Typeface.BOLD)
                } else {
                    name.setTextColor(ContextCompat.getColor(context, R.color.black))
                }

                if (pos == 0) {
                    name.setTextColor(ContextCompat.getColor(context, R.color.orange))
                    position.setTextColor(ContextCompat.getColor(context, R.color.orange))
                    bonuses.setTextColor(ContextCompat.getColor(context, R.color.orange))
                }



                name.text = user.name.toString()

                position.text = (pos + 1).toString()

                if (user.totalBonuses != null) {
                    bonuses.text = user.totalBonuses.toString()
                } else {
                    bonuses.text = "0"
                }
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}