// CreationCreateRepository.kt
package info.sul_game.model

import CreationCreateApi
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import info.sul_game.utils.TokenUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class CreationCreateRepository(private val api: CreationCreateApi, private val context: Context) {

    suspend fun sendCreationData(
        basePostId: String,
        memberId: String,
        sourceType: String,
        basePost: String,
        thumbnailIcon: String,
        isLiked: Boolean,
        isBookmarked: Boolean,
        introduction: String,
        title: String,
        description: String,
        gameFiles: List<File>,
        imageUrls: List<String>,
        gameTags: List<Int>,
        introLyricsInGame: String,
        introFiles: List<File>,
        introMultipartFileInGame: List<File>,
        lyrics: String,
        introType: String,
        introTags: List<String>,
        introMediaUrlFromGame: String
    ): Response<CreationGame> {
        // RequestBody 생성
        val basePostIdBody = basePostId.toRequestBody("text/plain".toMediaTypeOrNull())
        val memberIdBody = memberId.toRequestBody("text/plain".toMediaTypeOrNull())
        val sourceTypeBody = sourceType.toRequestBody("text/plain".toMediaTypeOrNull())
        val basePostBody = basePost.toRequestBody("text/plain".toMediaTypeOrNull())
        val thumbnailIconBody = thumbnailIcon.toRequestBody("text/plain".toMediaTypeOrNull())
        val isLikedBody = isLiked.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val isBookmarkedBody = isBookmarked.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val introductionBody = introduction.toRequestBody("text/plain".toMediaTypeOrNull())
        val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val imageUrlsBody = imageUrls.joinToString(",").toRequestBody("text/plain".toMediaTypeOrNull())
        val gameTagsBody = gameTags.joinToString(",").toRequestBody("text/plain".toMediaTypeOrNull())
        val introLyricsInGameBody = introLyricsInGame.toRequestBody("text/plain".toMediaTypeOrNull())
        val isCreatorInfoPrivateBody = "false".toRequestBody("text/plain".toMediaTypeOrNull()) // 필요 시 변경
        val lyricsBody = lyrics.toRequestBody("text/plain".toMediaTypeOrNull())
        val introTypeBody = introType.toRequestBody("text/plain".toMediaTypeOrNull())
        val introTagsBody = introTags.joinToString(",").toRequestBody("text/plain".toMediaTypeOrNull())
        val introMediaUrlFromGameBody = introMediaUrlFromGame.toRequestBody("text/plain".toMediaTypeOrNull())
        val token = "Bearer ${TokenUtil().getRefreshToken(context)}"

        // MultipartBody.Part 리스트 생성
        val gameMultipartFiles = gameFiles.map { file ->
            MultipartBody.Part.createFormData(
                "gameMultipartFiles",
                file.name,
                file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
        }

        val introMultipartFiles = introFiles.map { file ->
            MultipartBody.Part.createFormData(
                "introMultipartFiles",
                file.name,
                file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
        }


        return api.sendCreatePost(
            token = token,
            basePostId = basePostIdBody,
            memberId = memberIdBody,
            sourceType = sourceTypeBody,
            basePost = basePostBody,
            thumbnailIcon = thumbnailIconBody,
            isLiked = isLikedBody,
            isBookmarked = isBookmarkedBody,
            introduction = introductionBody,
            title = titleBody,
            description = descriptionBody,
            gameMultipartFiles = gameMultipartFiles,
            imageUrls = imageUrlsBody,
            gameTags = gameTagsBody,
            introLyricsInGame = introLyricsInGameBody,
            isCreatorInfoPrivate = isCreatorInfoPrivateBody,
            lyrics = lyricsBody,
            introType = introTypeBody,
            introTags = introTagsBody,
            introMultipartFiles = introMultipartFiles,
            introMediaUrlFromGame = introMediaUrlFromGameBody
            // relatedOfficialGameId 제거
        )
    }
}
