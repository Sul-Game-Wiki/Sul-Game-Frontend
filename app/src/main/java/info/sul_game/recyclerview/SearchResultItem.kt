package info.sul_game.recyclerview

import info.sul_game.model.Member

data class SearchResultItem(
    val createdDate: String,
    val basePostId: Long,
    val title: String,
    val introduction: String,
    val member: Member,
    val commentCount: Int,
    val views: Int,
    val likes: Int,
    val thumbnailIcon: String,
    val creatorInfoPrivate: Boolean

)
