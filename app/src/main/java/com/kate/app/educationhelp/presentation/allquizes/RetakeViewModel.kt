package com.kate.app.educationhelp.presentation.allquizes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.usecases.GetAllTopicsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RetakeViewModel: ViewModel() {

    val topic = MutableLiveData<Topic?>(null)

    fun getTopic(id: String){
        viewModelScope.launch {
            topic.postValue(GetAllTopicsUseCase(MyBackendRepository).execute().firstOrNull {
                it.id == id
            })
        }
    }
}