package info.sul_game.model

import java.util.Date

data class Member(
    val createdDate: String,
    val updatedDate: String,
    val isDeleted: Boolean,
    val memberId: Long,
    val email: String,
    val nickname: String,
    val birthDate: String,
    val profileUrl: String,
    val role: String,
    val university: String,
    val isUniversityPublic: Boolean,
    val accountStatus: String,
    val isNotificationEnabled: Boolean,
    val isNicknameModified : Boolean,
    val isProfileImageModified : Boolean,
    val infoPopupVisible: Boolean,
    val lastLoginTime: String,
    val refreshToken : String
)