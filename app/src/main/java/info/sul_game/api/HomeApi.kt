package info.sul_game.api

import info.sul_game.model.HomeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface HomeApi {
    @Multipart
    @POST("home/get")
    fun getHomeData(
        @Part("test") test: String
    ): Call<HomeResponse>
}
