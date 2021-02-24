package com.kate.app.educationhelp.domain.models

data class Topic(
    var id: String? = null,
    var title: String? = null,
    var body: String? = null,
    var images: List<String>? = null,
    var videos: List<String>? = null,
    var testsId: List<String>? = null,
    var grade: Int? = null,
    var subject: String? = null

) {

}