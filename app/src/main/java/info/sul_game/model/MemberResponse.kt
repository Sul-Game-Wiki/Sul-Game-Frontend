package info.sul_game.model

import info.sul_game.data.source.remote.BasePost
import java.util.Date

data class MemberResponse (
    // BasePost
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
    val dailyScore : Int,
    val weeklyScore : Int,
    val sourceType : String,
    val thumbnailIcon : String,
    val creatorInfoIsPrivate : Boolean,

    //Bookmark
    val bookmarkedOfficialGameIds : List<BasePost>,
    val bookmarkedCreationGameIds : List<BasePost>,
    val bookmarkedIntroIds : List<BasePost>,

    //LikedPost
    val likedOfficalGames : List<BasePost>,
    val likedCreationGames : List<BasePost>,
    val likedIntros : List<BasePost>,

    //Member
    val member: Member,
    val memberInteraction: MemberInteraction,

    //Exp
    val exp : Int,
    val expRank : Int,
    val expRankPercentile : Int,
    val nextLevelExp : Int,
    val remainingExpForNextLevel : Int,
    val progressPercentToNextLevel : Int,
    val rankChange : Int
)