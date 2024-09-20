package info.sul_game.api

import info.sul_game.model.OfficialGamesResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OfficialGameListApi {
    @Multipart
    @POST("home/posts")
    fun getOfficialDetails(
        @Part("pageNumber") pageNumber: RequestBody,
        @Part("pageSize") pageSize: RequestBody,
        @Part("postType") postType: RequestBody,
        @Part("sortBy") sortBy: RequestBody,
        @Part("direction") direction: RequestBody

    ): Call<OfficialGamesResponse>
}