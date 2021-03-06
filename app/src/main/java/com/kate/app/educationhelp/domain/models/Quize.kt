package com.kate.app.educationhelp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quize(
    var id: String? = null,
    var topicId: String? = null,
    var tests: List<String>? = null,
    val title: String? = null,
    val subject: String? = null,
    val grade: Int? = null
) : Parcelable {}