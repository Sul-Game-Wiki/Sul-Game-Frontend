package com.example.sul_game_frontend_practice1.retrofit

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.sul_game_frontend_practice1.MyApplication
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

object NetworkManager {

    fun getProfile(memberId : Long, context: Context) {
        // API 호출
        RetrofitClient.apiService
            .getMemberProfile(memberId)
            .enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                    if (response.isSuccessful) {
                        // API 응답이 성공적일 때 데이터 처리
                        val profileResponse = response.body()
                        profileResponse?.let {
                            val app = context.applicationContext as MyApplication
                            app.member = it.member
                            app.memberContentInteraction = it.memberContentInteraction

                            Log.d("API_RESPONSE", "Member: ${it.member}")
                            Log.d("API_RESPONSE", "MemberContentInteraction: ${it.memberContentInteraction}")

                        } ?: run {
                            Log.e("API_ERROR", "Response body is null")
                        }
                    } else {
                        Log.e("API_ERROR", "Error: ${response.code()} ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    // 네트워크 오류 또는 기타 오류 처리
                    Log.e("API_ERROR", "Failure: ${t.message}")
                }
            })
    }

    fun updateNickname(memberId: Long, newNickname: String, context: Context) {
        val memberIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), memberId.toString())
        val nicknameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), newNickname)

        // API 호출
        RetrofitClient.apiService
            .updateNickname(memberIdBody, nicknameBody)
            .enqueue(object : Callback<Member> {
                override fun onResponse(call: Call<Member>, response: Response<Member>) {
                    if (response.isSuccessful) {
                        // API 응답이 성공적일 때 데이터 처리
                        val nicknameResponse = response.body()
                        nicknameResponse?.let {
                            val app = context.applicationContext as MyApplication
                            app.member = it
                            Log.d("API_RESPONSE", "Member: $it")
                            Toast.makeText(context, "닉네임 변경 성공!", Toast.LENGTH_SHORT).show()

                            getProfile(memberId, context)
                        } ?: run {
                            Log.e("API_ERROR", "Response body is null")
                        }
                    } else {
                        Log.e("API_ERROR", "Error body: ${response.errorBody()?.string()}")
                        // 상태 코드로 예외를 구분
                        when (response.code()) {
                            409 -> Log.e("API_ERROR", "이미 존재하는 닉네임입니다!")
                            else -> Log.e("API_ERROR", "Error ${response.code()}: ${response.message()}")
                        }
                    }
                }

                override fun onFailure(call: Call<Member>, t: Throwable) {
                    // 네트워크 오류 또는 기타 오류 처리
                    Log.e("API_ERROR", "Failure: ${t.message}")
                }
            })
    }

    fun updateProfileImage(memberId: Long, imageFile: File, context: Context) {
        val memberIdBody = RequestBody.create("text/plain".toMediaTypeOrNull(), memberId.toString())
        val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

        RetrofitClient.apiService.updateProfileImage(memberIdBody, body)
            .enqueue(object : Callback<Member> {
                override fun onResponse(call: Call<Member>, response: Response<Member>) {
                    if (response.isSuccessful) {
                        val member = response.body()
                        Toast.makeText(context, "업로드 성공! ${member?.nickname}", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("UPLOAD_ERROR", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Member>, t: Throwable) {
                    Log.e("UPLOAD_ERROR", "Failure: ${t.message}")
                }
            })
    }
}