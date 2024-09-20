package info.sul_game.api

import info.sul_game.model.OfficialGamesResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RelatedSearchGameListApi {
    @Multipart
    @POST("official/get-all")
    fun getOfficialDetails(
        @Part("basePostId") basePostId: RequestBody
    ): Call<OfficialGamesResponse>
}
