package com.kate.app.educationhelp.presentation.quize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.usecases.AddBonusesToUser
import kotlinx.coroutines.launch

class QuizeResultsViewModel : ViewModel() {

    fun updateBonuses(number: Int) {
        viewModelScope.launch {
            AddBonusesToUser(MyBackendRepository).execute(
                Firebase.auth.currentUser?.uid ?: "",
                number
            )
        }
    }
}