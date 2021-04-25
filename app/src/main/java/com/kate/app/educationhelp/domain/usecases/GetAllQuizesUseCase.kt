package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.domain.models.Quize

class GetAllQuizesUseCase(private val repository: IRepository) {
    suspend fun execute(): List<Quize> = repository.getAllQuizes()
}