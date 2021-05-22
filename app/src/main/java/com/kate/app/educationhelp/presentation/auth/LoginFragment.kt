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
import com.kate.app.educationhelp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private var email: String = ""
    private var password: String = ""

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

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

        binding.signin.setOnClickListener {
            if (email.isNotBlank() && password.isNotBlank()) {
                authViewModel.signIn(email, password)
            } else {
                binding.email.error = getString(R.string.cannot_be_empty)
                binding.password.error = getString(R.string.cannot_be_empty)
            }
        }

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        authViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                AuthViewModel.AuthState.Authorized -> {
                    findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
                    activity?.finish()
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