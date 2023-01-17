package com.example.uwlapp.models
import com.google.gson.annotations.SerializedName

data class ResponseProductList(
    @SerializedName("product")
    var product: List<ProductResponse>
)

//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/
