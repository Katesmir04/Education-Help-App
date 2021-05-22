package com.kate.app.educationhelp.presentation.allquizes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.usecases.GetAllQuizesUseCase
import com.kate.app.educationhelp.domain.usecases.GetPassedQuizesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllQuizesViewModel : ViewModel() {

    private val _quizesState = MutableLiveData<QuizesListState>(QuizesListState.Loading)
    val quizesState: LiveData<QuizesListState>
        get() = _quizesState

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            _quizesState.postValue(
                QuizesListState.Loaded(
                    content = GetAllQuizesUseCase(
                        MyBackendRepository
                    ).execute(),
                    passedQuizes = GetPassedQuizesUseCase(MyBackendRepository).execute(
                        FirebaseAuth.getInstance().currentUser?.uid ?: ""
                    )
                )
            )
        }
    }
}