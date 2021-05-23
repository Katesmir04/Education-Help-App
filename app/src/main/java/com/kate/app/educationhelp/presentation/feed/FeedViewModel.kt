package com.kate.app.educationhelp.presentation.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.models.User
import com.kate.app.educationhelp.domain.usecases.GetAllTopicsUseCase
import com.kate.app.educationhelp.domain.usecases.GetPassedQuizesUseCase
import com.kate.app.educationhelp.domain.usecases.GetUserInfoUseCase
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    val recommendedTopic = MutableLiveData<Topic?>(null)

    val userInfo = MutableLiveData<User?>(null)

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val result = GetUserInfoUseCase(MyBackendRepository).execute(
                FirebaseAuth.getInstance().currentUser?.uid ?: ""
            )
            userInfo.postValue(
                result
            ).also {
                loadRecommendedTopic(result)
            }
        }
    }


    private fun loadRecommendedTopic(user: User) {
        viewModelScope.launch {

            val probablyRecommendations =
                GetAllTopicsUseCase(MyBackendRepository).execute().filter {
                    it.subject == user.favoriteSubject && it.grade == user.grade
                }

            val passedTopicsIds = GetPassedQuizesUseCase(MyBackendRepository).execute(
                FirebaseAuth.getInstance().currentUser?.uid ?: ""
            ).map {
                it.quize.topicId
            }

            val random = probablyRecommendations.filterNot {
                passedTopicsIds.contains(it.id)
            }.randomOrNull()

            if (random == null) {
                recommendedTopic.postValue(probablyRecommendations.randomOrNull())
            } else {
                recommendedTopic.postValue(random)
            }

        }
    }

}