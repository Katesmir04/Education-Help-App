package com.kate.app.educationhelp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Test(
    var id: String? = null,
    var title: String? = null,
    var topic: String? = null,
    var grade: Int? = null,
    var subject: String? = null,
    var type: Int? = null,
    var answer: List<String>? = null,
    var correct_answer: String? = null,
    var bonus: Int? = null

) : Parcelable {}