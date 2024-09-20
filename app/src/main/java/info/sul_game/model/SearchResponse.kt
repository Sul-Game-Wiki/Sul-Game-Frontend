package info.sul_game.model

import info.sul_game.recyclerview.SearchResultItem

data class SearchResponse(
//    val intros : List<Intro>,
//    val officialGames : List<OfficialGame>,
//    val creationGames : List<CreationGame>
    val intros : List<SearchResultItem>,
    val officialGames : List<SearchResultItem>,
    val creationGames : List<SearchResultItem>
)
