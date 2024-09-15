package info.sul_game.data.source.remote

import info.sul_game.model.Member
import java.util.Date

data class BasePost(
    val createdDate: Date,
    val updatedDate: Date,
    val isDeleted : Boolean,
    val isUpdated : Boolean,
    val basePostId : Int,
    val title : String,
    val introduction : String,
    val description : String,
    val likes : Int,
    val likedMemberIds : List<Int>,
    val views : Int,
    val reportedCount : Int,
    val member: Member,
    val dailyScore : Int,
    val weeklyScore : Int,
    val sourceType : String,
    val thumbnailIcon : String,
    val creatorInfoIsPrivate : Boolean
)