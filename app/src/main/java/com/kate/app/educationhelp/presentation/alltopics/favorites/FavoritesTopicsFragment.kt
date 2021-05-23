package com.kate.app.educationhelp.presentation.alltopics.favorites

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.google.android.material.chip.Chip
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentFavoritesTopicsBinding
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.presentation.alltopics.AllTopicsAdapter
import com.kate.app.educationhelp.presentation.alltopics.TopicsListState
import com.kate.app.educationhelp.presentation.topicdescr.TopicDescriptionFragmentArgs


class FavoritesTopicsFragment : Fragment() {

    private val binding: FragmentFavoritesTopicsBinding by lazy {
        FragmentFavoritesTopicsBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<FavoritesTopicsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AllTopicsAdapter(requireContext()) {
            onTopicClicked(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, true)
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(4))
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

        var lastCheckedId = View.NO_ID

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == View.NO_ID) {
                group.check(lastCheckedId)
                return@setOnCheckedChangeListener
            }

            lastCheckedId = checkedId

            adapter.submitList(viewModel.filterListByName(binding.root.findViewById<Chip>(checkedId).text.toString()))
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

    }

    private fun onTopicClicked(topic: Topic) {
        val args = TopicDescriptionFragmentArgs(topic = topic).toBundle()
        findNavController().navigate(
            R.id.action_favoritesTopicsFragment_to_topicDescriptionFragment,
            args
        )
    }

    class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
        fun getItemOffsetss(
            outRect: Rect, view: View?,
            parent: RecyclerView, state: RecyclerView.State?
        ) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space

            if (parent.getChildLayoutPosition(view!!) == 0) {
                outRect.top = space
            } else {
                outRect.top = 0
            }
        }
    }
}