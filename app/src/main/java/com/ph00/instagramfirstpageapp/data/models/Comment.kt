package com.ph00.instagramfirstpageapp.data.models

import com.squareup.moshi.Json

data class Comment(
    @field:Json(name = "username") val userName: String,
    @field:Json(name = "comment") val comment: String
)