package com.kate.app.educationhelp.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.shape.CornerFamily
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentGradeAndSubjectBinding


class GradeAndSubjectFragment : Fragment() {

    private val binding by lazy {
        FragmentGradeAndSubjectBinding.inflate(layoutInflater)
    }

    private val args by navArgs<GradeAndSubjectFragmentArgs>()

    private val authViewModel by viewModels<AuthViewModel>()

    private var grade: String = ""
    private var subject: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radius = 32f
        binding.image.shapeAppearanceModel = binding.image.shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()

        binding.chooseGrade.editText?.setText(R.string.choose_grade)
        binding.chooseFavs.editText?.setText(R.string.fav_sub)

        val items = resources.getStringArray(R.array.grades).toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.expandable_list_item_grade, items)
        (binding.chooseGrade.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.chooseGrade.editText?.doOnTextChanged { text, start, before, count ->
            grade = text?.toString()?.substringBefore(" класс").toString()
        }

        val favs = resources.getStringArray(R.array.fav_subject).toList()
        val adapter2 = ArrayAdapter(requireContext(), R.layout.expandable_list_item_grade, favs)
        (binding.chooseFavs.editText as? AutoCompleteTextView)?.setAdapter(adapter2)

        binding.chooseFavs.editText?.doOnTextChanged { text, start, before, count ->
            subject = text?.toString().toString()
        }

        binding.signin.setOnClickListener {
            authViewModel.signUp(
                name = args.name,
                email = args.email,
                password = args.password,
                favSub = subject,
                grade = grade.toInt()
            )
        }

        authViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                AuthViewModel.AuthState.Authorized -> {
                    findNavController().navigate(R.id.action_gradeAndSubjectFragment_to_mainActivity)
                }
                AuthViewModel.AuthState.Failed -> {
                    Toast.makeText(requireContext(), R.string.toast_no_such_user, Toast.LENGTH_LONG)
                        .show()

                    " ".apply {
                        binding.chooseFavs.error = this
                        binding.chooseGrade.error = this
                    }
                }
            }
        }

    }
}