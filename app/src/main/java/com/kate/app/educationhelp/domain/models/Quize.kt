package com.kate.app.educationhelp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quize(
    var id: String? = null,
    var grade: Int? = null,
    var subject: String? = null,
    var title: String? = null,
    var tests: List<String>? = null
) : Parcelable {}