package com.konradt.messagemanager.retrofit

import com.konradt.messagemanager.models.PostsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    fun getPosts(): Call<PostsResponse>
}