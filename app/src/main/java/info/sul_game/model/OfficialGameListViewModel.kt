package info.sul_game.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.sul_game.config.RetrofitClient
import info.sul_game.recyclerview.OfficialGameListItem
import info.sul_game.recyclerview.RelatedGameListItem
import info.sul_game.utils.Direction
import info.sul_game.utils.SortBy
import info.sul_game.utils.SourceType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class OfficialGameListViewModel: ViewModel() {
    private val _gameList = MutableLiveData<List<OfficialGameListItem>>()
    val gameList: LiveData<List<OfficialGameListItem>> get() = _gameList

    fun loadGameList(
        pageNumber: Int?,
        pageSize: Int?,
        postType: SourceType?,
        sortBy: SortBy?,
        direction: Direction?
    ) {
        // 각 파라미터를 개별적으로 RequestBody로 변환
        val pageNumberBody = RequestBody.create(MultipartBody.FORM, pageNumber?.toString() ?: "0")
        val pageSizeBody = RequestBody.create(MultipartBody.FORM, pageSize?.toString() ?: "10")
        val postTypeBody = RequestBody.create(MultipartBody.FORM, postType?.name ?: "OFFICIAL")
        val sortByBody = RequestBody.create(MultipartBody.FORM, sortBy?.name ?: "CREATED_DATE")
        val directionBody = RequestBody.create(MultipartBody.FORM, direction?.name ?: "ASC")

        // API 호출
        val call: Call<OfficialGamesResponse> = RetrofitClient.officialGameListService.getOfficialDetails(
            pageNumberBody,
            pageSizeBody,
            postTypeBody,
            sortByBody,
            directionBody
        )
        call.enqueue(object : retrofit2.Callback<OfficialGamesResponse> {
            override fun onResponse(
                call: Call<OfficialGamesResponse>,
                response: Response<OfficialGamesResponse>
            ) {
                if (response.isSuccessful) {
                    val officialGamesResponse = response.body()
                    val OfficialGameListItems = officialGamesResponse?.officialGames?.map { game ->
                        OfficialGameListItem(game.title, game.introduction,game.likes,game.commentCount,game.createdDate,game.thumbnailIcon,game.member.nickname)
                    } ?: emptyList()

                    _gameList.postValue(OfficialGameListItems)
                } else {
                    _gameList.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<OfficialGamesResponse>, t: Throwable) {
                _gameList.postValue(emptyList())
            }
        })
    }
}
