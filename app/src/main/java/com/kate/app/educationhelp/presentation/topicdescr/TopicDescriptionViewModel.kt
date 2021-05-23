package com.kate.app.educationhelp.presentation.topicdescr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopicDescriptionViewModel : ViewModel() {

    val quize = MutableLiveData<Quize?>(null)

    val isQuizeCompleted = MutableLiveData(false)

    val isInFavorite = MutableLiveData(false)

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

    fun addFavorite(topic: Topic) {
        viewModelScope.launch {
            AddFavoriteTopicUseCase(MyBackendRepository).execute(
                FirebaseAuth.getInstance().currentUser?.uid ?: "", topic
            ).also {
                isInFavorite.postValue(true)
            }
        }
    }

    fun removeFavorite(topic: Topic) {
        viewModelScope.launch {
            RemoveFavoriteTopicUseCase(MyBackendRepository).execute(
                FirebaseAuth.getInstance().currentUser?.uid ?: "", topic
            ).also {
                isInFavorite.postValue(false)
            }
        }
    }

    fun updateFavoriteStatus(topic: Topic) {
        viewModelScope.launch {
            isInFavorite.postValue(GetFavoritesTopicsUseCase(MyBackendRepository).execute(
                FirebaseAuth.getInstance().currentUser?.uid ?: ""
            ).any {
                it.id == topic.id
            })
        }
    }
}