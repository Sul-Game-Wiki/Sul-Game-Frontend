package info.sul_game.model

data class LatestCreationGame(
    val createdDate: String,
    val updatedDate: String,
    val isDeleted: Boolean,
    val isUpdated: Boolean,
    val basePostId: Int,
    val title: String,
    val introduction: String,
    val description: String,
    val likes: Int,
    val likedMemberIds: List<Int>,
    val views: Int,
    val reportedCount: Int,
    val member: Member,
    val dailyScore: Int,
    val weeklyScore: Int,
    val totalScore: Int,
    val commentCount: Int,
    val sourceType: String,
    val thumbnailIcon: String,
    val officialGame: OfficialGame,
    val introLyricsInGamePost: String,
    val introMediaFileInGamePostUrl: String,
    val gameTags: List<String>,
    val creatorInfoPrivate: Boolean
)