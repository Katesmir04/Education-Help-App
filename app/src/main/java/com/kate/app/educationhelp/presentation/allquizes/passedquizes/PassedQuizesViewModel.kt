package com.kate.app.educationhelp.presentation.allquizes.passedquizes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.usecases.GetPassedQuizesUseCase
import com.kate.app.educationhelp.presentation.quize.QuizeResultsViewModel.QuizeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PassedQuizesViewModel : ViewModel() {

    private val _quizesState = MutableLiveData<PassedQuizesListState>(PassedQuizesListState.Loading)
    val quizesState: LiveData<PassedQuizesListState>
        get() = _quizesState

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            _quizesState.postValue(
                PassedQuizesListState.Loaded(
                    passedQuizes = GetPassedQuizesUseCase(MyBackendRepository).execute(
                        FirebaseAuth.getInstance().currentUser?.uid ?: ""
                    )
                )
            )
        }
    }

    fun filterListByName(name: String): List<QuizeItem> {
        if (name != "Все") {
            when (_quizesState.value) {
                is PassedQuizesListState.Loaded -> {
                    val newList: List<QuizeItem> =
                        (_quizesState.value as PassedQuizesListState.Loaded).passedQuizes.filter {
                            it.quize.subject == name
                        }

                    return newList
                }
                else -> return emptyList()
            }
        } else {
            return (_quizesState.value as PassedQuizesListState.Loaded).passedQuizes
        }

    }
}

sealed class PassedQuizesListState {
    object Loading : PassedQuizesListState()
    data class Loaded(val passedQuizes: List<QuizeItem>) :
        PassedQuizesListState()
}