package info.sul_game.model

import info.sul_game.data.source.remote.BasePost

data class GameDetails(val basePostId: Int,
                       val memberId: Int,
                       val sourceType: String,
                       val basePost: BasePost,
                       val thumbnailIcon: String,
                       val isLiked: Boolean,
                       val isBookmarked: Boolean,
                       val introduction: String,
                       val title: String,
                       val description: String,
                       val gameMultipartFiles: List<String>,
                       val imageUrls: List<String>,
                       val gameTags: List<String>,
                       val introLyrics: String,
                       val introMediaFileInGamePost: String,
                       val isCreatorInfoPrivate: Boolean,
                       val relatedOfficialGameId: Int,
                       val lyrics: String,
                       val introType: String,
                       val introTags: List<String>,
                       val introMultipartFiles: List<String>)
