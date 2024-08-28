package com.example.sul_game_frontend_practice1.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // 마이페이지
    @GET("members/profile")
    fun getMemberProfile(
        @Query("memberId") memberId: Long
    ): Call<ProfileResponse>

    // 좋아요한 글
    @GET("members/liked-post")
    fun getLikedPosts(
        @Query("memberId") memberId: Long
    ): Call<LikedPostsResponse>

    // 즐겨찾기한 글
    @GET("members/bookmarked-post")
    fun getBookmarkedPosts(
        @Query("memberId") memberId: Long
    ): Call<BookmarkedPostsResponse>

    // 회원 닉네임 업데이트
    @POST("members/nickname")
    fun updateNickname(
        @Query("memberId") memberId: Long,
        @Query("nickname") nickname: String
    ): Call<NicknameResponse>
}