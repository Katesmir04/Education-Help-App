package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository.AuthRepository
import com.kate.app.educationhelp.domain.models.User

class PushNewUserUseCase(private val repository: AuthRepository) {
    suspend fun execute(user: User) = repository.pushNewUser(user)
}