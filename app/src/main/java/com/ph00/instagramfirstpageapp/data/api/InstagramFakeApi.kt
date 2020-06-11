package com.ph00.instagramfirstpageapp.data.api

import com.ph00.instagramfirstpageapp.data.models.Ad
import com.ph00.instagramfirstpageapp.data.models.Post
import retrofit2.http.GET

interface InstagramFakeApi {

    @GET("b/5ee241501f9e4e57881bacd0/latest")
    suspend fun getAllPosts(): List<Post>

    @GET("b/5ee25d6d655d87580c483e52/latest")
    suspend fun getAllAds(): List<Ad>
}