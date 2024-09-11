package info.sul_game.data.source.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("home")
    fun getHomeData(): Call<ApiResponse>

    @GET("profile")
    fun getProfile(
        @Query("memberId") memberId: Long
    ): Call<ProfileResponse>

    @GET("liked-post")
    fun getLikedPosts(
        @Query("memberId") memberId: Long
    ): Call<LikedPostsResponse>

    @GET("bookmarked-post")
    fun getBookmarkedPosts(
        @Query("memberId") memberId: Long
    ): Call<BookmarkedPostsResponse>
}