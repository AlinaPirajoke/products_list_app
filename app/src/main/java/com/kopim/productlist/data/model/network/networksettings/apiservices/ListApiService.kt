package com.kopim.productlist.data.model.network.networksettings.apiservices

import com.kopim.productlist.data.model.network.apimodels.addtocart.AddToCartRequestData
import com.kopim.productlist.data.model.network.apimodels.addtocart.AddToCartResponseData
import com.kopim.productlist.data.model.network.apimodels.getcart.GetCartResponseData
import com.kopim.productlist.data.model.network.apimodels.removefromcart.CheckProductRequestData
import com.kopim.productlist.data.model.network.apimodels.renameitem.RenameItemRequestData
import com.kopim.productlist.data.model.network.apimodels.searchhints.SearchHintsResponseItemData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ListApiService: ApiService {

    @GET("get_list")
    suspend fun getProducts(
        @Query("cart") cartId: Long
    ): Response<GetCartResponseData>

    @POST("add_to_cart")
    suspend fun addToCart(
        @Body product: AddToCartRequestData
    ): Response<AddToCartResponseData>

    @GET("search_hints")
    suspend fun getHints(
        @Query("query") key: String
    ): Response<List<SearchHintsResponseItemData>>

    @POST("remove_from_cart")
    suspend fun checkProduct(
        @Body product: CheckProductRequestData
    ): Response<Unit>

    @POST("rename_item")
    suspend fun renameItem(
        @Body items: RenameItemRequestData
    ): Response<Unit>
}