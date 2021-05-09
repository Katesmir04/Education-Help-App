package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.domain.models.User

class GetUserInfoUseCase(private val repository: IRepository) {
    suspend fun execute(id: String): User = repository.getUserInfo(id)
}