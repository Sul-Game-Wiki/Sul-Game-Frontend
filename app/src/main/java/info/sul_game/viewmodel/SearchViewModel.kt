package info.sul_game.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.sul_game.config.RetrofitClient
import info.sul_game.model.MemberResponse
import info.sul_game.model.SearchResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableLiveData<SearchResponse>()
    val searchResults: LiveData<SearchResponse> get() = _searchResults

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun searchPosts(query: String) {
        val queryBody = createRequestBody(query)

        RetrofitClient.searchApiService
            .searchPosts(queryBody).enqueue(object :
                Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {

                        _searchResults.value = response.body()  // 성공적으로 데이터를 받으면 LiveData에 저장
                        Log.d("ViewModel", "${_searchResults.value}")
                    } else {
                        _error.value = "Error: ${response.code()}"  // 에러 발생 시 처리
                        Log.e("ViewModel", "${_error.value}")
                    }
                }

                override fun onFailure(
                    call: Call<SearchResponse>,
                    t: Throwable
                ) {
                    _error.value = "Failure: ${t.message}"  // 네트워크 오류 처리
                    Log.e("ViewModel", "${_error.value}")
                }

            })
    }

    private fun createRequestBody(value: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, value)
    }
}