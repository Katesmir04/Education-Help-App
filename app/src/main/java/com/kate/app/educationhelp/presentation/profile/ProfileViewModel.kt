package com.kate.app.educationhelp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.User
import com.kate.app.educationhelp.domain.usecases.GetUserInfoUseCase
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _data = MutableLiveData<DataState>()
    val data: LiveData<DataState>
        get() = _data

    val userInfo = MutableLiveData<User>()

    enum class DataState {
        Retrieved, Failed, Loading
    }

    fun retrieveUserInfo() {
        _data.postValue(DataState.Loading)

        viewModelScope.launch {

            try {
                Firebase.auth.uid?.let {
                    userInfo.postValue(
                        GetUserInfoUseCase(MyBackendRepository).execute(
                            it
                        )
                    )
                }

                _data.postValue(DataState.Retrieved)
            } catch (ex: Throwable) {
                _data.postValue(DataState.Failed)
            }

        }
    }

}