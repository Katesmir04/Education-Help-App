package com.kate.app.educationhelp.presentation.topicdescr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.usecases.GetAllQuizesUseCase
import com.kate.app.educationhelp.domain.usecases.GetAllTopicsUseCase
import com.kate.app.educationhelp.domain.usecases.GetTestsByTopicIdUseCase
import com.kate.app.educationhelp.presentation.allquizes.QuizesListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopicDescriptionViewModel : ViewModel() {

    val quize = MutableLiveData<Quize?>(null)

    fun loadQuize(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            quize.postValue(GetAllQuizesUseCase(MyBackendRepository).execute().firstOrNull {
                it.id == id
            })
        }

    }
}