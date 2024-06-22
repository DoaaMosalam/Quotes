package com.opportunity.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Quotes(
    @SerializedName("_id")
    var _id: String,
    var id:Long?,
    val author: String,
    val content: String,
    val tags: List<String>,
    val authorSlug: String,
    val length: Int,
    val dateAdded: String,
    val dateModified: String,
)
data class QuotesResponse(
    val count: Int,
    val totalCount: Int,
    val page: Int,
    val totalPages: Int,
    val lastItemIndex: Int,
    val results: List<Quotes>
)
