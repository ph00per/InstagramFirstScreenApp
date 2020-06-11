package com.ph00.instagramfirstpageapp.data.models

import com.squareup.moshi.Json

data class Ad(@field:Json(name = "id") val id: Int, @field:Json(name = "imageUrl") val imageUrl: String)