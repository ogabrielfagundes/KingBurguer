package com.example.kingburguer.compose.product

import com.example.kingburguer.data.CouponResponse
import com.example.kingburguer.data.ProductDetailResponse

data class ProductUiState(
    val isLoading: Boolean = false,
    val productDetail: ProductDetailResponse? = null,
    val coupon: CouponResponse? = null,
    val error: String? = null
)
