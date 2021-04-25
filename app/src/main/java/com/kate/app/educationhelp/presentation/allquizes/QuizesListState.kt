package com.kate.app.educationhelp.presentation.allquizes

import com.kate.app.educationhelp.domain.models.Quize

sealed class QuizesListState {
    object Loading : QuizesListState()
    data class Loaded(val content: List<Quize>) : QuizesListState()
}