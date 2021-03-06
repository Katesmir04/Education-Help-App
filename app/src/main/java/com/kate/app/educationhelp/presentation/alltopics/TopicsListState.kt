package com.kate.app.educationhelp.presentation.alltopics

import com.kate.app.educationhelp.domain.models.Topic

sealed class TopicsListState {
    object Loading : TopicsListState()
    data class Loaded(val content: List<Topic>) : TopicsListState()
}