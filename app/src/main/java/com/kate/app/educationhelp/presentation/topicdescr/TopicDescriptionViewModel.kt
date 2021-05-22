package com.kate.app.educationhelp.presentation.topicdescr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.domain.usecases.GetAllQuizesUseCase
import com.kate.app.educationhelp.domain.usecases.GetPassedQuizesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopicDescriptionViewModel : ViewModel() {

    val quize = MutableLiveData<Quize?>(null)

    val isQuizeCompleted = MutableLiveData(false)

    fun loadQuize(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            quize.postValue(GetAllQuizesUseCase(MyBackendRepository).execute().firstOrNull {
                it.id == id
            })


            isQuizeCompleted.postValue(GetPassedQuizesUseCase(MyBackendRepository).execute(
                FirebaseAuth.getInstance().currentUser?.uid ?: ""
            ).any {
                it.quize.id?.trim() == id.trim()
            })
        }
    }
}