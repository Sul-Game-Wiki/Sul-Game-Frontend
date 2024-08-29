package com.example.sul_game_frontend_practice1.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    @Multipart
    @POST("members/nickname")
    fun updateNickname(
        @Part("memberId") memberId: RequestBody,
        @Part("nickname") nickname: RequestBody
    ): Call<Member>

    // 회원 프로필 이미지 업데이트
    @Multipart
    @POST("members/profile-image")
     fun updateProfileImage(
        @Part("memberId") memberId: RequestBody,
        @Part("multipartFile") multipartFile: MultipartBody.Part
    ): Call<Member>
}