package com.ph00.instagramfirstpageapp.data.models

import com.squareup.moshi.Json

data class Story(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "userAvatar") val storyImgUrl: String,
    @field:Json(name = "userName") val userName: String
)