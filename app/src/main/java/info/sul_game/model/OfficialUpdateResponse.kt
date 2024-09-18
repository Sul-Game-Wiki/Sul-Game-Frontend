package info.sul_game.model

import info.sul_game.data.source.remote.BasePost

data class OfficialUpdateResponse(
    val basePost: BasePost?,
    val intros: List<Intro>?,
    val baseMedias: List<BaseMedia>?,
    val baseMediaMap: BaseMediaMap?,
    val officialGame: OfficialGame?,
    val creationGame: CreationGame?
)
