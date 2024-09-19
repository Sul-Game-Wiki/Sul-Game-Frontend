package info.sul_game.api

import info.sul_game.model.MemberResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SearchApi {
    // 전체 게시물 검색
    @Multipart
    @POST("search/all")
    fun searchPosts(
        @Part("query") query: RequestBody
    ): Call<MemberResponse>
}