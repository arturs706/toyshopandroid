package com.example.uwlapp.models
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("productid")
    var productid: String,
    @SerializedName("availableqty")
    var availableqty: Int,
    @SerializedName("created_at")
    var created_at: String,
    @SerializedName("descr")
    var descr: String,
    @SerializedName("price")
    var price: String,
    @SerializedName("proddescr")
    var proddescr: String,
    @SerializedName("prodname")
    var prodname: String,
    @SerializedName("prodsku")
    var prodsku: String,
    @SerializedName("imageone")
    var imageone: String,
    @SerializedName("imagetwo")
    var imagetwo: String,
    @SerializedName("imagethree")
    var imagethree: String,
    @SerializedName("imagefour")
    var imagefour: String,
    @SerializedName("isFavorite")
    var isFavorite: Boolean
)

//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

