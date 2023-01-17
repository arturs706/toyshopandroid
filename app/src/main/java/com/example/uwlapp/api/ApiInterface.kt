package com.example.uwlapp.api

import com.example.myapp.models.*
import com.example.uwlapp.models.FavouriteItemResponse
import com.example.uwlapp.models.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

//routes for the api
interface ApiInterface {
    @GET("/api/v1/users")
    suspend fun getAllUsers(@Header("Authorization") token: String): Response<ResponseListUsers>

    @POST("/api/v1/users/login")
    suspend fun loginUser(@Body request: RequestBody): Response<LoginResponse>

    @POST("/api/v1/users/register")
    suspend fun registerUser(@Body request: RequestBody): Response<RegistrationSuccess>

    @GET("/api/v1/products/{productid}")
    suspend fun getSingleProduct(@Path("productid") productid: String): Response<ResponseProductList>

    @GET("/api/v1/products")
    suspend fun getAllProducts(): Response<ResponseProductsList>

    @POST("api/v1/products/create-payment-intent")
    suspend fun stripeCallback(@Header("Authorization") token: String, @Body request: RequestBody): Response<StripeResponse>

    @POST("api/v1/createorders")
    suspend fun createorder(@Header("Authorization") token: String, @Body request: RequestBody): Response<Createorder>

    @POST("api/v1/createorders/items")
    suspend fun orderItems(@Header("Authorization") token: String, @Body request: RequestBody): Response<Createorder>

    @GET("/api/v1/users/{usid}")
    suspend fun getSingleUser(@Header("Authorization") token: String, @Path("usid") usid: String): Response<ResponseListUsers>

    @PUT("api/v1/products/{productid}")
    suspend fun updateProductQty(@Header("Authorization") token: String, @Path("productid") productid: String, @Body request: RequestBody): Response<SuccessResponse>

    @GET("api/v1/orders/{usid}")
    suspend fun getOrders(@Header("Authorization") token: String, @Path("usid") usid: String): Response<Createorder>

    @GET("api/v1/orders")
    suspend fun getAdminOrders(@Header("Authorization") token: String): Response<AdminOrderResponse>


    @GET("api/v1/orders/singleorder/{productid}")
    suspend fun getSingleOrder(@Header("Authorization") token: String, @Path("productid") productid: String): Response<SingleOrderResponse>

    @POST("/api/v1/users/resetpassword")
    suspend fun emailRecover(@Body request: RequestBody): Response<PasswordEmailResponse>

    @GET("api/v1/favourites/{userid}")
    suspend fun getFavourites(@Header("Authorization") token: String, @Path("userid") userid: String): Response<FavouriteItemResponse>

    @DELETE("api/v1/favourites/{userid}/{productid}")
    suspend fun delFavourite(@Header("Authorization") token: String, @Path("userid") userid: String, @Path("productid") productid: String): Response<DelFavourite>

    @POST("api/v1/favourites/{userid}/{productid}")
    suspend fun addFavourite(@Header("Authorization") token: String, @Path("userid") userid: String, @Path("productid") productid: String): Response<DelFavourite>

    @PUT("api/v1/users/updateuser/{tokenid}")
    suspend fun updateUser(@Header("Authorization") token: String, @Path("tokenid") tokenid: String, @Body request: RequestBody): Response<UpdateUserResponse>

    @PUT("api/v1/users/updateuser/address/{tokenid}")
    suspend fun updateUserAddress(@Header("Authorization") token: String, @Path("tokenid") tokenid: String, @Body request: RequestBody): Response<UpdateUserResponse>

    @PUT("api/v1/users/updateuser/password/{tokenid}")
    suspend fun updateUserPassword(@Header("Authorization") token: String, @Path("tokenid") tokenid: String, @Body request: RequestBody): Response<UpdateUserResponse>
}

//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/
