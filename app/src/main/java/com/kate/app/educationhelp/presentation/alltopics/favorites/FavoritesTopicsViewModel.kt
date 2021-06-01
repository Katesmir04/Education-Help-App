package com.kate.app.educationhelp.presentation.alltopics.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.Topic
import com.kate.app.educationhelp.domain.usecases.GetFavoritesTopicsUseCase
import com.kate.app.educationhelp.presentation.alltopics.TopicsListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesTopicsViewModel : ViewModel() {

    private val _topicsState = MutableLiveData<TopicsListState>(TopicsListState.Loading)
    val topicsState: LiveData<TopicsListState>
        get() = _topicsState

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            _topicsState.postValue(
                TopicsListState.Loaded(
                    GetFavoritesTopicsUseCase(
                        MyBackendRepository
                    ).execute(FirebaseAuth.getInstance().currentUser?.uid ?: "").reversed()
                )
            )
        }
    }

    fun filterListByName(name: String): List<Topic> {
        if (name != "Все") {
            when (_topicsState.value) {
                is TopicsListState.Loaded -> {
                    val newList: List<Topic> =
                        (_topicsState.value as TopicsListState.Loaded).content.filter {
                            it.subject == name
                        }

                    return newList
                }
                else -> return emptyList()
            }
        } else {
            return (_topicsState.value as TopicsListState.Loaded).content
        }

    }
}