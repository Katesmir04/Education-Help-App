package com.kate.app.educationhelp.presentation.allquizes

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentRetakeBinding
import com.kate.app.educationhelp.presentation.quize.QuizeFragmentArgs


class RetakeFragment : DialogFragment() {
    private val binding by lazy {
        FragmentRetakeBinding.inflate(layoutInflater)
    }

    private val args by lazy {
        RetakeFragmentArgs.fromBundle(requireArguments())
    }

    private val viewModel by viewModels<RetakeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        dialog?.let {
//            it.window?.run {
//                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                requestFeature(Window.FEATURE_NO_TITLE)
//            }
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.quize.topicId?.let { viewModel.getTopic(it) }

        with(binding) {

            title.text = args.quize.title
            body.text = context?.resources?.getString(
                R.string.subject_and_grade,
                args.quize.subject,
                args.quize.grade.toString()
            )

            passedBonuses.text = args.bonuses
            passedCount.text = args.completed

            wholeCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.light_red
                )
            )

            button.setOnClickListener {
                findNavController().navigate(
                    R.id.action_retakeFragment_to_quizeFragment, QuizeFragmentArgs(
                        topicId = args.quize.topicId ?: "",
                        startFragment = R.id.allQuizesFragment,
                        topic = viewModel.topic.value,
                        quize = args.quize
                    ).toBundle()
                )
            }
        }
    }
}