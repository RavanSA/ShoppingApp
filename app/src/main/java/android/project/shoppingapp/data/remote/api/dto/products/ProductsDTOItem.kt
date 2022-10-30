package android.project.shoppingapp.data.remote.api.dto.products

data class ProductsDTOItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: ProductsRating,
    val title: String
)