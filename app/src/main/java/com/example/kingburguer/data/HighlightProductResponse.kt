package com.example.kingburguer.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class HighlightProductResponse(
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("picture_url")
    val pictureUrl: String,
    @SerializedName("created_date")
    val createdDate: Date
)
