package com.kate.app.educationhelp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String? = null,
    var avatar: String? = null,
    var favoriteSubject: String? = null,
    var favorites: List<String>? = null,
    var grade: Int? = null,
    var name: String? = null,
    var passedQuizes: List<String>? = null,
    var recentlyViewed: List<String>? = null,
    var status: String? = null,
    var totalBonuses: Int? = null
) : Parcelable {}

