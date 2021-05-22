package com.kate.app.educationhelp.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.shape.CornerFamily
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private val binding: FragmentSignUpBinding by lazy {
        FragmentSignUpBinding.inflate(layoutInflater)
    }

    private var email: String = ""
    private var password: String = ""
    private var name: String = ""

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        binding.email.editText?.doAfterTextChanged { text ->
            binding.email.error =
                if (text?.length == 0) getString(R.string.cannot_be_empty) else null
            email = text.toString()
        }

        binding.password.editText?.doAfterTextChanged { text ->
            binding.password.error =
                if (text?.length == 0) getString(R.string.cannot_be_empty) else null
            password = text.toString()
        }

        binding.name.editText?.doAfterTextChanged { text ->
            binding.name.error =
                if (text?.length == 0) getString(R.string.cannot_be_empty) else null
            name = text.toString()
        }

        binding.signin.setOnClickListener {
            if (email.isNotBlank() && password.isNotBlank()) {
                findNavController().navigate(
                    R.id.action_signUpFragment_to_gradeAndSubjectFragment,
                    GradeAndSubjectFragmentArgs(
                        name = name,
                        email = email,
                        password = password
                    ).toBundle()
                )
            } else {
                binding.email.error = getString(R.string.cannot_be_empty)
                binding.password.error = getString(R.string.cannot_be_empty)
            }
        }

        authViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                AuthViewModel.AuthState.Authorized -> {
                    findNavController().navigate(R.id.action_signUpFragment_to_mainActivity)
                }
                AuthViewModel.AuthState.Failed -> {
                    Toast.makeText(requireContext(), R.string.toast_no_such_user, Toast.LENGTH_LONG)
                        .show()

                    " ".apply {
                        binding.email.error = this
                        binding.password.error = this
                    }
                }
            }
        }

    }
}