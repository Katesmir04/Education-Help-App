package com.kate.app.educationhelp.presentation.allquizes

import com.kate.app.educationhelp.domain.models.Quize
import com.kate.app.educationhelp.presentation.quize.QuizeResultsViewModel.QuizeItem

sealed class QuizesListState {
    object Loading : QuizesListState()
    data class Loaded(val content: List<Quize>, val passedQuizes: List<QuizeItem>) :
        QuizesListState()
}