package info.sul_game.data.source.remote

import java.util.Date

data class Member(
    val createdDate: Date,
    val updatedDate: Date,
    val isDeleted: Boolean,
    val memberId: Long,
    val email: String,
    val nickname: String,
    val birthDate: String,
    val profileUrl: String,
    val role: String,
    val college: String,
    val exp: Int,
    val expLevel: String,
    val isUniversityPublic: Boolean,
    val accountStatus: String,
    val isNotificationEnabled: Boolean,
    val infoPopupVisible: Boolean,
    val lastLoginTime: Date
)

data class Intro(
    val createdDate: String,
    val updatedDate: String,
    val isDeleted: Boolean,
    val basePostId: Int,
    val title: String,
    val description: String,
    val likes: Int,
    val likedMemberIds: List<String>,
    val views: Int,
    val reportedCount: Int,
    val member: Member,
    val dailyScore: Int,
    val weeklyScore: Int,
    val lyrics: String
)

data class ApiResponse(
    val latestIntros: List<Intro>
)