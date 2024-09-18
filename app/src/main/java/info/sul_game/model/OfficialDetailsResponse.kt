package info.sul_game.model

import com.google.gson.annotations.SerializedName
import info.sul_game.data.source.remote.BasePost

data class OfficialDetailsResponse(
    val gameDetails: GameDetails?,
    val basePost: BasePost?,
    val member: Member?
)