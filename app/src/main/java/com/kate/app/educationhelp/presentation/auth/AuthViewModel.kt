package com.kate.app.educationhelp.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import com.kate.app.educationhelp.domain.models.User
import com.kate.app.educationhelp.domain.usecases.PushNewUserUseCase
import com.kate.app.educationhelp.domain.usecases.SignInUserUseCase
import com.kate.app.educationhelp.domain.usecases.SignUpUserUseCase
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _state = MutableLiveData<AuthState>()
    val state: LiveData<AuthState>
        get() = _state

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                SignInUserUseCase(MyBackendRepository).execute(email, password).user?.let {
                    _state.postValue(AuthState.Authorized)
                } ?: run {
                    _state.postValue(AuthState.Failed)
                }
            } catch (e: FirebaseException) {
                _state.postValue(AuthState.Failed)
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            try {
                SignUpUserUseCase(MyBackendRepository).execute(
                    email.trim(),
                    password.trim()
                ).user?.let {
                    _state.postValue(AuthState.Authorized)

                    PushNewUserUseCase(MyBackendRepository).execute(
                        user = emptyUser(
                            it.uid,
                            "Vasya"
                        )
                    )
                } ?: run {
                    _state.postValue(AuthState.Failed)
                }
            } catch (e: FirebaseException) {
                _state.postValue(AuthState.Failed)
            }
        }
    }

    enum class AuthState {
        Authorized, Failed, None
    }

    private fun emptyUser(id: String, name: String) =
        User(
            id = id,
            avatar = "",
            favoriteSubject = "",
            favorites = emptyList(),
            grade = 0,
            name = name,
            passedQuizes = emptyList(),
            recentlyViewed = emptyList(),
            status = "",
            totalBonuses = 0
        )
}