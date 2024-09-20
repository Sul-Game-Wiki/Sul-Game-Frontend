package info.sul_game.model

import android.util.Log
import androidx.lifecycle.ViewModel
import info.sul_game.config.RetrofitClient
import info.sul_game.recyclerview.RelatedGameListItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class RelatedSearchGameListViewModel : ViewModel() {

    // 서버에서 게임 리스트를 로드하는 함수
    fun loadGameList(basePostId: String?, onDataLoaded: (List<RelatedGameListItem>) -> Unit) {
        // basePostId가 null이거나 비어있으면 빈 문자열을 Multipart로 전송
        val requestBody: RequestBody = basePostId?.toRequestBody("multipart/form-data".toMediaTypeOrNull()) ?:
        "".toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val call: Call<OfficialGamesResponse> = RetrofitClient.relatedSearchGameListService.getOfficialDetails(requestBody)

        call.enqueue(object : retrofit2.Callback<OfficialGamesResponse> {
            override fun onResponse(
                call: Call<OfficialGamesResponse>,
                response: Response<OfficialGamesResponse>
            ) {
                if (response.isSuccessful) {
                    val officialGamesResponse = response.body()
                    val relatedGameListItems = officialGamesResponse?.officialGames?.map { game ->
                        RelatedGameListItem(game.title, game.introduction)
                    } ?: emptyList()

                    onDataLoaded(relatedGameListItems) // 데이터를 로드한 후 콜백을 호출하여 결과를 전달
                } else {
                    Log.e("RelatedSearchGameListViewModel", "Error fetching all data: ${response.errorBody()?.string()}")
                    onDataLoaded(emptyList()) // 에러 시 빈 리스트 전달
                }
            }

            override fun onFailure(call: Call<OfficialGamesResponse>, t: Throwable) {
                Log.e("RelatedSearchGameListViewModel", "Error fetching all data", t)
                onDataLoaded(emptyList()) // 실패 시 빈 리스트 전달
            }
        })
    }


    // 게임 리스트를 필터링하는 함수
    fun filterGameList(data: List<RelatedGameListItem>, query: String): List<RelatedGameListItem> {
        return data.filter {
            it.title.contains(query, ignoreCase = true) || it.introduction.contains(query, ignoreCase = true)
        }
    }
}
