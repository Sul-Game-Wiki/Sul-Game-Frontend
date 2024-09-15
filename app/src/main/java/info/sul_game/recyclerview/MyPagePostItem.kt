package info.sul_game.ui.mypage

data class MyPagePostItem(
    val time: String,
    val basePostId : Int,
    val title : String,
    val introduction : String,
    val likes : Int,
    val comments : Int,
    val nickname: String,
    val thumbnailIcon : String,
    val creatorInfoIsPrivate : Boolean
)