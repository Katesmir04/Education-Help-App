package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.domain.models.Topic

class GetFavoritesTopicsUseCase(private val repository: IRepository) {
    suspend fun execute(id: String): List<Topic> = repository.getFavorites(id)
}