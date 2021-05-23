package com.kate.app.educationhelp.presentation.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.models.User
import com.kate.app.educationhelp.domain.usecases.*
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    val recommendedTopic = MutableLiveData<Topic?>(null)

    val lastFavorite = MutableLiveData<Topic?>(null)

    val userInfo = MutableLiveData<User?>(null)

    var ratingList = MutableLiveData<UsersRatingInfo?>(null)

    data class UsersRatingInfo(
        val user: User,
        val list: List<User>,
        val position: Int,
        val inFive: Boolean
    )

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
                loadLastFavorite()
                loadAllUsers(result)
            }
        }
    }

    private fun loadAllUsers(user: User) {
        viewModelScope.launch {
            val result = GetAllUsersUseCase(MyBackendRepository).execute()
            buildRatingList(result, user)
        }
    }

    private suspend fun buildRatingList(users: List<User>, user: User) {
        val filteredUsersByBonuses = users.sortedBy {
            it.totalBonuses
        }.reversed()

        var userPosition = -1

        filteredUsersByBonuses.forEachIndexed { index, userList ->
            if (userList.id == user.id) {
                userPosition = index
            }
        }

        if (filteredUsersByBonuses.size > 5) {

            if (userPosition > 4) {
                ratingList.postValue(
                    UsersRatingInfo(
                        user = user,
                        list = filteredUsersByBonuses.subList(0, 5),
                        position = userPosition,
                        inFive = false
                    )
                )
            } else {
                ratingList.postValue(
                    UsersRatingInfo(
                        user = user,
                        list = filteredUsersByBonuses.subList(0, 5),
                        position = userPosition,
                        inFive = true
                    )
                )
            }

        } else {
            ratingList.postValue(
                UsersRatingInfo(
                    user = user,
                    list = filteredUsersByBonuses,
                    position = userPosition,
                    inFive = true
                )
            )
        }
    }

    private fun loadLastFavorite() {
        viewModelScope.launch {
            val result = GetFavoritesTopicsUseCase(MyBackendRepository).execute(
                FirebaseAuth.getInstance().currentUser?.uid ?: ""
            )

            lastFavorite.postValue(result.lastOrNull())
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