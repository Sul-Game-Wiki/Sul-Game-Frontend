package info.sul_game.model

import info.sul_game.data.source.remote.BasePost

data class BaseMedia(
    val createdDate: String,
    val updatedDate: String,
    val isDeleted: Boolean,
    val isUpdated: Boolean,
    val baseMediaId: Int,
    val mediaUrl: String,
    val fileSize: Int,
    val mediaType: String,
    val basePost: BasePost
)
