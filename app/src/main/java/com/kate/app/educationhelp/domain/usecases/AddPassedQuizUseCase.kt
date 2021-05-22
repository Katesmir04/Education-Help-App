package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository
import com.kate.app.educationhelp.presentation.quize.QuizeResultsViewModel.QuizeItem

class AddPassedQuizUseCase(private val repository: IRepository) {
    suspend fun execute(quizeItem: QuizeItem) = repository.addPassedQuize(quizeItem)
}