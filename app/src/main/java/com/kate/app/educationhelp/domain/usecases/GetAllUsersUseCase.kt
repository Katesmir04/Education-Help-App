package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.domain.models.User

class GetAllUsersUseCase(private val repository: IRepository) {
    suspend fun execute(): List<User> = repository.getAllUsers()
}