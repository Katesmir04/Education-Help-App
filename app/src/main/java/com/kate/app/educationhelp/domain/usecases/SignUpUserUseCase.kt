package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository

class SignUpUserUseCase(private val repository: IRepository.AuthRepository) {
    suspend fun execute(email: String, password: String) = repository.signUp(email, password)
}