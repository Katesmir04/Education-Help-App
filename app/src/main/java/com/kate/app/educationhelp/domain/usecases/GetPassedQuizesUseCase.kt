package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.presentation.quize.QuizeResultsViewModel

class GetPassedQuizesUseCase(private val repository: IRepository) {
    suspend fun execute(id: String): List<QuizeResultsViewModel.QuizeItem> = repository.getPassedQuizes(id)
}