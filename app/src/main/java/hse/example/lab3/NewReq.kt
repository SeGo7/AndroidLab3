package hse.example.lab3

data class NewReq (
    val status: String,
    val totalReq: Int,
    val results: List<NewsItem>,
    val nextPage: ULong
)