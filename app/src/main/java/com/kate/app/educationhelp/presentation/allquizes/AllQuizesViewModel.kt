package com.kate.app.educationhelp.presentation.allquizes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.usecases.GetAllQuizesUseCase
import com.kate.app.educationhelp.domain.usecases.GetPassedQuizesUseCase
import com.kate.app.educationhelp.presentation.alltopics.TopicsListState
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

    fun filterListByName(name: String): List<Quize> {
        if (name != "Все") {
            when (_quizesState.value) {
                is QuizesListState.Loaded -> {
                    val newList: List<Quize> =
                        (_quizesState.value as QuizesListState.Loaded).content.filter {
                            it.subject == name
                        }

                    return newList
                }
                else -> return emptyList()
            }
        } else {
            return (_quizesState.value as QuizesListState.Loaded).content
        }

    }
}