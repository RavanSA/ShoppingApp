package android.project.shoppingapp.data.remote.api.mapper

import android.project.shoppingapp.data.local.database.entity.CategoryEntity
import android.project.shoppingapp.data.local.database.entity.ProductsEntity
import android.project.shoppingapp.data.model.Category
import android.project.shoppingapp.data.model.Products
import android.project.shoppingapp.data.remote.api.dto.categories.CategoriesDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem

fun toCategoriesEntity(categories: String): CategoryEntity {
    return CategoryEntity(
            id = 0,
            categories = categories
        )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = 0,
        category = categories
    )
}


