package info.sul_game.api

import info.sul_game.model.OfficialDetailsResponse
import info.sul_game.model.OfficialGamesResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface GameListApi {
    @Multipart
    @POST("official/get-all")
    fun getOfficialDetails(
        @Part("basePostId") basePostId: RequestBody
    ): Call<OfficialGamesResponse>
}
