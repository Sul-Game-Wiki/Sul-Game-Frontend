package info.sul_game.data.source.remote

import java.lang.reflect.Array

data class OfficialCreateResponse(
    val basePostId: Int,
    val memberId: Int,
    val description: String,
    val sourceType: String,
    val basePost: BasePost,
    val introductionr:String,
    val title:String,
    val multipartFiles: Array,
    val imageUrls: Array,
    val lyrics: String,
    val introtype:String

)


