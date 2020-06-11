package com.ph00.instagramfirstpageapp.data.repositories

import com.ph00.instagramfirstpageapp.data.api.InstagramFakeApi

class PostsRepository(private val instApi: InstagramFakeApi) {

    suspend fun getAllPosts() = instApi.getAllPosts()

    suspend fun getAllAds() = instApi.getAllAds()
}