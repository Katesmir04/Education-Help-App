package com.kate.app.educationhelp.presentation.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentFeedBinding
import com.kate.app.educationhelp.presentation.topicdescr.TopicDescriptionFragmentArgs

class FeedFragment : Fragment() {

    private val binding by lazy {
        FragmentFeedBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<FeedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.recommendedTopic.observe(viewLifecycleOwner) { topic ->
            topic?.let {
                binding.rTitle.text = it.title
            } ?: setNothing()
        }

        viewModel.lastFavorite.observe(viewLifecycleOwner) { topic ->
            topic?.let {
                binding.fTitle.text = it.title
            } ?: setNothingInFavs()
        }

        binding.favoritesCard.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_favoritesTopicsFragment)
        }

        binding.recommendedCard.setOnClickListener {
            viewModel.recommendedTopic.value?.let {
                findNavController().navigate(
                    R.id.action_feedFragment_to_topicDescriptionFragment,
                    TopicDescriptionFragmentArgs(topic = it).toBundle()
                )
            } ?: Toast.makeText(
                context,
                R.string.nothing_to_show_recommended_toast,
                Toast.LENGTH_LONG
            ).show()

        }

        val adapter = RatingAdapter(requireContext())

        binding.ratingList.adapter = adapter

        viewModel.ratingList.observe(viewLifecycleOwner) { usersInfoRating ->
            if (usersInfoRating != null) {
                if (usersInfoRating.inFive) {
                    adapter.setUser(usersInfoRating.user)
                    adapter.submitList(usersInfoRating.list)
                } else if (!usersInfoRating.inFive) {
                    adapter.submitList(usersInfoRating.list)

                    binding.userPositionContainer.visibility = View.VISIBLE
                    binding.divider.visibility = View.VISIBLE

                    binding.name.text = usersInfoRating.user.name
                    binding.position.text = usersInfoRating.position.toString()
                    if (usersInfoRating.user.totalBonuses != null) {
                        binding.bonuses.text = usersInfoRating.user.totalBonuses.toString()
                    } else {
                        binding.bonuses.text = "0"
                    }
                }
                binding.ratingList.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun setNothingInFavs() {
        binding.fTitle.text = resources.getString(R.string.no_favs)
    }

    private fun setNothing() {
        binding.rTitle.text = resources.getString(R.string.nothing_to_show_recommended)
    }

}