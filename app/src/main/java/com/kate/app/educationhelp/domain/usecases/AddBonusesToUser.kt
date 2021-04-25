package com.kate.app.educationhelp.domain.usecases

import com.kate.app.educationhelp.domain.irepositories.IRepository

class AddBonusesToUser(private val repository: IRepository) {
    suspend fun execute(id: String, number: Int) = repository.addBonusesToUser(id, number)
}