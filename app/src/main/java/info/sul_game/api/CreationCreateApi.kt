// CreationCreateApi.kt
import info.sul_game.model.CreationGame
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CreationCreateApi {
    @Multipart
    @POST("creation/create") // 실제 엔드포인트로 변경
    suspend fun sendCreatePost(
        @Header("Authorization") token: String,
        @Part("basePostId") basePostId: RequestBody?,
        @Part("memberId") memberId: RequestBody?,
        @Part("sourceType") sourceType: RequestBody,
        @Part("basePost") basePost: RequestBody?,
        @Part("thumbnailIcon") thumbnailIcon: RequestBody,
        @Part("isLiked") isLiked: RequestBody?,
        @Part("isBookmarked") isBookmarked: RequestBody?,
        @Part("introduction") introduction: RequestBody,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part gameMultipartFiles: List<MultipartBody.Part>,
        @Part("imageUrls") imageUrls: RequestBody?,
        @Part("gameTags") gameTags: RequestBody,
        @Part("introLyricsInGame") introLyricsInGame: RequestBody?,
        @Part("isCreatorInfoPrivate") isCreatorInfoPrivate: RequestBody,
        @Part("lyrics") lyrics: RequestBody,
        @Part("introType") introType: RequestBody?,
        @Part("introTags") introTags: RequestBody?,
        @Part introMultipartFiles: List<MultipartBody.Part>?,
        @Part("introMediaUrlFromGame") introMediaUrlFromGame: RequestBody?
    ): Response<CreationGame>
}
