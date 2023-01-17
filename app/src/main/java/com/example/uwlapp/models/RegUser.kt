package com.example.uwlapp.models
import com.google.gson.annotations.SerializedName


data class RegUser(
    @SerializedName("fullname")
    var fullname: String,
    @SerializedName("username")
    var username: String,
    @SerializedName("dob")
    var dob: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("mob_phone")
    var mob_phone: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("passwd")
    var passwd: String,
    @SerializedName("address")
    var address: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("postcode")
    var postcode: String
    )

//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

