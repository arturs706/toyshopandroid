package com.example.uwlapp.models
import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("status")
    var status: String,
    @SerializedName("message")
    var message: String,
    @SerializedName("access_token")
    var access_token: String,
    @SerializedName("refresh_token")
    var refresh_token: String
)
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/
