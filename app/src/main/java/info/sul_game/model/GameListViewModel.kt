package info.sul_game.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.sul_game.config.RetrofitClient
import info.sul_game.recyclerview.GameListItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class GameListViewModel : ViewModel() {
    private val _gameList = MutableLiveData<List<GameListItem>>()
    val gameList: LiveData<List<GameListItem>> get() = _gameList
    private val _filteredGameList = MutableLiveData<List<GameListItem>>()
    val filteredGameList: LiveData<List<GameListItem>> = _filteredGameList

    fun loadGameList(basePostId: String?) {
        val requestBody: RequestBody = if (basePostId.isNullOrEmpty()) {
            RequestBody.create(MultipartBody.FORM, "") // 빈 값을 전송할 경우
        } else {
            RequestBody.create(MultipartBody.FORM, basePostId) // 실제 basePostId 값을 전송
        }

        val call: Call<OfficialGamesResponse> = RetrofitClient.gameListService.getOfficialDetails(requestBody)

        call.enqueue(object : retrofit2.Callback<OfficialGamesResponse> {
            override fun onResponse(
                call: Call<OfficialGamesResponse>,
                response: Response<OfficialGamesResponse>
            ) {
                if (response.isSuccessful) {
                    val officialGamesResponse = response.body()
                    val gameListItems = officialGamesResponse?.officialGames?.map { game ->
                        GameListItem(game.title, game.introduction)
                    } ?: emptyList()

                    _gameList.postValue(gameListItems)
                } else {
                    Log.e("API Error", "Error fetching all data: ${response.errorBody()?.string()}")
                    _gameList.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<OfficialGamesResponse>, t: Throwable) {
                Log.e("MyFragment", "Error fetching all data", t)
                _gameList.postValue(emptyList())
            }
        })
    }
    fun filterGameList(query: String) {
        val data = _gameList.value ?: emptyList()
        val filteredData = data.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.introduction.contains(query, ignoreCase = true)
        }
        _filteredGameList.value = filteredData
    }
}
