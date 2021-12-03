package com.chores.newsapp.service

import com.chores.newsapp.model.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getNewsHeadLines(
        @Query("Country") country: String?,
        @Query("apiKey") apiKey : String?
    ): Call<ResponseNews>
}