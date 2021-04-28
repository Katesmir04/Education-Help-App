package com.kate.app.educationhelp.presentation.quize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.usecases.GetAllTopicsUseCase
import com.kate.app.educationhelp.domain.usecases.GetTestsByTopicIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizeViewModel : ViewModel() {

    private val _testsState = MutableLiveData<TestsListState>(TestsListState.Loading)
    val testsState: LiveData<TestsListState>
        get() = _testsState

    val topic = MutableLiveData<Topic?>(null)

    fun refreshData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _testsState.postValue(
                TestsListState.Loaded(
                    GetTestsByTopicIdUseCase(MyBackendRepository).execute(
                        id
                    )
                )
            )

            loadTopic(id)
        }

    }

    private suspend fun loadTopic(id: String) {
        topic.postValue(GetAllTopicsUseCase(MyBackendRepository).execute().firstOrNull {
            it.id == id
        })
    }
}