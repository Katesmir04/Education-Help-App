package com.kate.app.educationhelp.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kate.app.educationhelp.databinding.FragmentProfileBinding
import com.kate.app.educationhelp.domain.models.User
import com.kate.app.educationhelp.presentation.auth.LoginActivity


class ProfileFragment : Fragment() {
    private val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.retrieveUserInfo()

        viewModel.userInfo.observe(viewLifecycleOwner, ::handleUserInfo)

        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                ProfileViewModel.DataState.Retrieved -> {
                    binding.progress.visibility = View.GONE
                    binding.mainContentContainer.visibility = View.VISIBLE
                }
                ProfileViewModel.DataState.Failed -> {
                    Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_LONG)
                        .show()
                }
                ProfileViewModel.DataState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.mainContentContainer.visibility = View.GONE
                }
            }
        }
    }

    private fun handleUserInfo(userInfo: User) {
        with(binding) {
            name.text = userInfo.name
            email.text = Firebase.auth.currentUser?.email
            grade.text = (userInfo.grade ?: 0).toString()
            fCount.text = (userInfo.favorites?.size ?: 0).toString()
            sNumber.text = (userInfo.totalBonuses ?: 0).toString()
            rNumber.text = 1234.toString()
            tCount.text = (userInfo.passedQuizes?.size ?: 0).toString()
            logout.setOnClickListener {
                Firebase.auth.signOut()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                activity?.finish()
            }
        }
    }
}