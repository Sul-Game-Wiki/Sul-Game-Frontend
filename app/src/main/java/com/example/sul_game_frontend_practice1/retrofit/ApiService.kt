package com.example.sul_game_frontend_practice1.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("members/profile")
    fun getMemberProfile(@Query("memberId") memberId: Long): Call<ProfileResponse>

    @GET("members/liked-post")
    fun getLikedPosts(
        @Query("memberId") memberId: Long
    ): Call<LikedPostsResponse>

    @GET("members/bookmarked-post")
    fun getBookmarkedPosts(
        @Query("memberId") memberId: Long
    ): Call<BookmarkedPostsResponse>
}