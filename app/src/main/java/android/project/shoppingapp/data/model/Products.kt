package android.project.shoppingapp.data.model

import android.project.shoppingapp.data.remote.api.dto.products.ProductsRating

data class Products(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val ratingCount: Int,
    val ratingRate: Double,
    val title: String
)