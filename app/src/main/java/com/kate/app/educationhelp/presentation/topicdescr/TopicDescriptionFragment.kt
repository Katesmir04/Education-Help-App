package com.kate.app.educationhelp.presentation.topicdescr

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    private val viewModel by viewModels<TopicDescriptionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadQuize(topic.quizeIds?.first() ?: "")
        binding.apply {
            title.text = topic.title
            subject.text =
                getString(R.string.subject_and_grade, topic.subject, topic.grade.toString())

            body.text = topic.body?.replace("\n", "\n")

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
                    goToQuiz(topicId)
                }
            }

            more.setOnClickListener {
                if (more.text == resources.getString(R.string.details)) {
                    body.maxLines = Int.MAX_VALUE
                    body.ellipsize = null
                    more.text = resources.getString(R.string.hide)
                } else {
                    body.maxLines = 10
                    body.ellipsize = TextUtils.TruncateAt.END
                    more.text = resources.getString(R.string.details)
                }
            }
        }

    }

    private fun goToQuiz(topicId: String) {

        viewModel.quize.value?.let {
            findNavController().navigate(
                R.id.action_topicDescriptionFragment_to_quizeFragment,
                QuizeFragmentArgs(topicId = topicId, quize = it).toBundle()
            )
        } ?: Toast.makeText(requireContext(), "Тест не найден", Toast.LENGTH_LONG).show()


    }

}