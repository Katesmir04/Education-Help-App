package com.kate.app.educationhelp.presentation.topicdescr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentTopicDescriptionBinding
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.presentation.quize.QuizeFragmentArgs
import com.kate.app.educationhelp.presentation.topicdescr.images.ViewPagerInstructionsAdapter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class TopicDescriptionFragment : Fragment() {

    private val binding: FragmentTopicDescriptionBinding by lazy {
        FragmentTopicDescriptionBinding.inflate(layoutInflater)
    }

    private val topic: Topic by lazy {
        TopicDescriptionFragmentArgs.fromBundle(requireArguments()).topic
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            title.text = topic.title
            subject.text = getString(R.string.subject, topic.subject)
            grade.text = getString(R.string.grade, topic.grade.toString())
            body.text = topic.body

            pager.apply {
                adapter = topic.images?.let {
                    ViewPagerInstructionsAdapter(
                        requireContext(),
                        it
                    )
                }
            }
            lifecycle.addObserver(videos)
            videos.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val kek = "bSMZknDI6bg"
                    youTubePlayer.loadVideo(kek, 0F)
                }
            })

            testsT.setOnClickListener {
                topic.id?.let { topicId ->
                    findNavController().navigate(
                        R.id.action_topicDescriptionFragment_to_quizeFragment,
                        QuizeFragmentArgs(topicId = topicId).toBundle()
                    )
                }
            }
        }
    }

}