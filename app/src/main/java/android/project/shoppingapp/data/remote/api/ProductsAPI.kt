package android.project.shoppingapp.data.remote.api

import android.project.shoppingapp.data.remote.api.dto.products.ProductDTO
import android.project.shoppingapp.data.remote.api.dto.products.ProductsDTOItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsAPI {


    @GET("products")
    suspend fun getAllProducts() : List<ProductsDTOItem>

    @GET("products/{id}")
    suspend fun getProduct(@Path(value = "id") productId : Int) : ProductDTO

}