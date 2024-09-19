package info.sul_game.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.sul_game.config.RetrofitClient
import info.sul_game.data.source.remote.BasePost
import info.sul_game.model.Member
import info.sul_game.model.MemberResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberViewModel : ViewModel() {
    // 네트워크 응답 데이터를 LiveData로 래핑
    private val _memberResponse = MutableLiveData<MemberResponse>()
    val memberResponse: LiveData<MemberResponse> get() = _memberResponse

    private val _likedPosts = MutableLiveData<MemberResponse>()
    val likedPosts: LiveData<MemberResponse> get() = _likedPosts

    private val _bookmarkedPosts = MutableLiveData<MemberResponse>()
    val bookmarkedPosts: LiveData<MemberResponse> get() = _bookmarkedPosts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // 빈 파라미터 생성(Multipart일 경우 필수라서 빈 값 보내기)
    val emptyPart = createRequestBody("")

    // Retrofit을 사용한 네트워크 요청 처리
    fun getMemberProfile(token: String) {

        RetrofitClient.memberApiService
            .getMemberProfile(token, emptyPart).enqueue(object :
            Callback<MemberResponse> {
            override fun onResponse(call: Call<MemberResponse>, response: Response<MemberResponse>) {
                if (response.isSuccessful) {
                    _memberResponse.value = response.body()  // 성공적으로 데이터를 받으면 LiveData에 저장
                    Log.d("ViewModel", "${_memberResponse.value}")
                } else {
                    _error.value = "Error: ${response.code()}"  // 에러 발생 시 처리
                    Log.e("ViewModel", "${_error.value}")

                }
            }

            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                _error.value = "Failure: ${t.message}"  // 네트워크 오류 처리
                Log.e("ViewModel", "${_error.value}")
            }
        })
    }

    fun getLikedPosts(token: String) {
        RetrofitClient.memberApiService
            .getLikedPosts(token, emptyPart).enqueue(object :
            Callback<MemberResponse> {
                override fun onResponse(
                    call: Call<MemberResponse>,
                    response: Response<MemberResponse>
                ) {
                    if (response.isSuccessful) {
                        _likedPosts.value = response.body()  // 성공적으로 데이터를 받으면 LiveData에 저장
                        Log.d("ViewModel", "${_likedPosts.value}")
                    } else {
                        _error.value = "Error: ${response.code()}"  // 에러 발생 시 처리
                        Log.e("ViewModel", "${_error.value}")
                    }
                }

                override fun onFailure(
                    call: Call<MemberResponse>,
                    t: Throwable
                ) {
                    _error.value = "Failure: ${t.message}"  // 네트워크 오류 처리
                    Log.e("ViewModel", "${_error.value}")
                }

            })
    }

    fun getBookmarkedPosts(token: String) {
        RetrofitClient.memberApiService
            .getBookmarkedPosts(token, emptyPart).enqueue(object :
                Callback<MemberResponse> {
                override fun onResponse(
                    call: Call<MemberResponse>,
                    response: Response<MemberResponse>
                ) {
                    if (response.isSuccessful) {
                        _bookmarkedPosts.value = response.body()  // 성공적으로 데이터를 받으면 LiveData에 저장
                        Log.d("ViewModel", "${_bookmarkedPosts.value}")
                    } else {
                        _error.value = "Error: ${response.code()}"  // 에러 발생 시 처리
                        Log.e("ViewModel", "${_error.value}")
                    }
                }

                override fun onFailure(
                    call: Call<MemberResponse>,
                    t: Throwable
                ) {
                    _error.value = "Failure: ${t.message}"  // 네트워크 오류 처리
                    Log.e("ViewModel", "${_error.value}")
                }

            })
    }

    fun getMyPosts(token: String) {
        RetrofitClient.memberApiService
            .getMyPosts(token, emptyPart).enqueue(object :
                Callback<MemberResponse> {
                override fun onResponse(
                    call: Call<MemberResponse>,
                    response: Response<MemberResponse>
                ) {
                    if (response.isSuccessful) {
                        _bookmarkedPosts.value = response.body()  // 성공적으로 데이터를 받으면 LiveData에 저장
                        Log.d("ViewModel", "${_bookmarkedPosts.value}")
                    } else {
                        _error.value = "Error: ${response.code()}"  // 에러 발생 시 처리
                        Log.e("ViewModel", "${_error.value}")
                    }
                }

                override fun onFailure(
                    call: Call<MemberResponse>,
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

    private fun createMultipartBodyPart(name: String, jsonData: String): MultipartBody.Part {
        val requestBody = RequestBody.create(MultipartBody.FORM, jsonData)
        return MultipartBody.Part.createFormData(name, null, requestBody)
    }
}