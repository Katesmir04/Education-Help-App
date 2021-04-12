package com.kate.app.educationhelp.presentation.quize

import com.kate.app.educationhelp.domain.models.Test

sealed class TestsListState {
    object Loading : TestsListState()
    data class Loaded(val content: List<Test>) : TestsListState()
}