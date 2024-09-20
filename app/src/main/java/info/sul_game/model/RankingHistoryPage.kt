package info.sul_game.model

data class RankingHistoryPage(
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val content: List<RankingHistory>,
    val number: Int,
    val sort: Sort,
    val pageable: Pageable,
    val first: Boolean,
    val last: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)

data class RankingHistory(
    val createdDate: String,
    val updatedDate: String,
    val isDeleted: Boolean,
    val isUpdated: Boolean,
    val id: Int,
    val member: Member,
    val recordDate: String,
    val rank: Int,
    val exp: Int,
    val rankChangeStatus: String,
    val rankChange: Int,
    val updateTime: String
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class Pageable(
    val offset: Long,
    val sort: Sort,
    val paged: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val unpaged: Boolean
)