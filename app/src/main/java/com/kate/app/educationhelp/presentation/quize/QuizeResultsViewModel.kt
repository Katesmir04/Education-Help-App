package com.kate.app.educationhelp.presentation.quize

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.domain.usecases.AddBonusesToUser
import com.kate.app.educationhelp.domain.usecases.AddPassedQuizUseCase
import com.kate.app.educationhelp.presentation.quize.QuizeFragment.QuizeResults
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.launch

class QuizeResultsViewModel : ViewModel() {

    fun updateQuizeStatus(
        bonuses: Int,
        quize: Quize,
        results: List<QuizeResults>,
        comleted: (Boolean) -> Unit
    ) {
        viewModelScope.launch {

            AddBonusesToUser(MyBackendRepository).execute(
                Firebase.auth.currentUser?.uid ?: "",
                bonuses
            )

            FirebaseAuth.getInstance().currentUser?.let {
                AddPassedQuizUseCase(MyBackendRepository).execute(
                    QuizeItem(
                        quize = quize,
                        results = results,
                        bonuses = bonuses,
                        it.uid
                    )
                )
            }

            comleted(true)
        }
    }

    @Parcelize
    data class QuizeItem(
        val quize: Quize,
        val results: List<QuizeResults>,
        val bonuses: Int,
        val userId: String
    ) : Parcelable
}