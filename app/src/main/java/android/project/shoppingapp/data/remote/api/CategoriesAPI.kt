package android.project.shoppingapp.data.remote.api

import android.project.shoppingapp.data.remote.api.dto.categories.CategoriesDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesAPI {

    @GET("products/categories")
    suspend fun getAllCategories() : CategoriesDTO

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path(value = "category") category : String) : List<ProductsDTOItem>

}