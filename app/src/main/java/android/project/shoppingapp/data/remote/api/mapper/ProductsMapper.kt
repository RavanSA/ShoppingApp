package android.project.shoppingapp.data.remote.api.mapper

import android.project.shoppingapp.data.local.database.entity.ProductsEntity
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem

fun ProductsEntity.toProducts(): Products {
    return Products(
        category = category,
        description = description,
        id = id,
        image = image,
        price = price,
        ratingCount = ratingCount,
        ratingRate = ratingRate,
        title = title,
    )
}


fun ProductsDTOItem.toProductsEntity(): ProductsEntity {
    return ProductsEntity(
        category = category,
        description = description,
        id = id,
        image = image,
        price = price,
        ratingCount = rating.count,
        ratingRate = rating.rate,
        title = title,
    )
}