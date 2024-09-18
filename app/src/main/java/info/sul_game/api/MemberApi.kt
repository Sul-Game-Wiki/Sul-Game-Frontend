package info.sul_game.api

import info.sul_game.model.Member
import info.sul_game.model.MemberRequest
import info.sul_game.model.MemberResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MemberApi {
    // 닉네임 중복체크
    @Multipart
    @POST("members/check-nickname")
    fun checkNickName(
        @Header("Authorization") token: String,
        @Part("nickname") nickname: RequestBody,
    ): Call<MemberResponse>

    // 닉네임 중복체크
    @Multipart
    @POST("members/complete-registration")
    fun completeRegistration(
        @Header("Authorization") token: String,
        @Part("nickname") nickname: RequestBody,
        @Part("birthDate") birthDate: RequestBody,
        @Part("university") university: RequestBody,
    ): Call<MemberResponse>

    // 마이페이지
    @Multipart
    @POST("members/profile")
    fun getMemberProfile(
        @Header("Authorization") token: String,
        @Part("part") part : RequestBody
    ): Call<MemberResponse>

    // 좋아요한 글
    @Multipart
    @POST("members/liked-post")
    fun getLikedPosts(
        @Header("Authorization") token: String,
        @Part("part") part : RequestBody
    ): Call<MemberResponse>

    // 즐겨찾기한 글
    @Multipart
    @POST("members/bookmarked-post")
    fun getBookmarkedPosts(
        @Header("Authorization") token: String,
        @Part("part") part : RequestBody
    ): Call<MemberResponse>

    // 회원 닉네임 업데이트
    @Multipart
    @POST("members/nickname")
    fun updateNickname(
        @Header("Authorization") token: String,
        @Part("nickname") nickname: RequestBody
    ): Call<Member>

    // 회원 프로필 이미지 업데이트
    @Multipart
    @POST("members/profile-image")
    fun updateProfileImage(
        @Header("Authorization") token: String,
        @Part("multipartFile") multipartFile: MultipartBody.Part
    ): Call<Member>
}
