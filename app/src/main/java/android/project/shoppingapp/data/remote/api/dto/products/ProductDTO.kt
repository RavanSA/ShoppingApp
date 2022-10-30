package android.project.shoppingapp.data.remote.api.dto.products

data class ProductDTO(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: ProductRating,
    val title: String
)