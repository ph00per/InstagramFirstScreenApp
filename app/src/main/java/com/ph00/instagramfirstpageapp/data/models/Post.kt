package com.ph00.instagramfirstpageapp.data.models

import com.squareup.moshi.Json

data class Post(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "userName") val userName: String,
    @field:Json(name = "userProfileImageUrl") val userProfileImageUrl: String,
    @field:Json(name = "imageUrl") val imageUrl: String,
    @field:Json(name = "userLocation") val userLocation: String,
    @field:Json(name = "likesCount") val likes: Int,
    @field:Json(name = "comments") val comments: List<Comment>?
)