package com.kate.app.educationhelp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Topic(
    var id: String? = null,
    var title: String? = null,
    var body: String? = null,
    var images: List<String>? = null,
    var videos: List<String>? = null,
    var testsId: List<String>? = null,
    var grade: Int? = null,
    var subject: String? = null

):Parcelable {

}