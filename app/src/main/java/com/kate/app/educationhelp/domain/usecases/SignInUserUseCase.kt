package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository

class SignInUserUseCase(private val repository: IRepository.AuthRepository) {
    suspend fun execute(email: String, password: String) = repository.signIn(email, password)
}