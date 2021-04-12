package com.kate.app.educationhelp.presentation.quize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.usecases.GetTestsByTopicIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizeViewModel : ViewModel() {

    private val _testsState = MutableLiveData<TestsListState>(TestsListState.Loading)
    val testsState: LiveData<TestsListState>
        get() = _testsState

    fun refreshData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _testsState.postValue(
                TestsListState.Loaded(
                    GetTestsByTopicIdUseCase(MyBackendRepository).execute(
                        id
                    )
                )
            )
        }
    }
}