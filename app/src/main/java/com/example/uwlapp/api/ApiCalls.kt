package com.example.uwlapp.api

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.uwlapp.customconfigs.customconfigs.largeShow
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.customconfigs.customconfigs.smallShow


object ApiCalls {
    suspend fun productList(continueToProduct: Button?, tvw: TextView?, tv1: TextView?,tv2: TextView?, imageR: ImageView?, imageRTwo: ImageView?, imageRThree: ImageView?, progressBar: ProgressBar?) {
        try {
            progressBar?.visibility = View.VISIBLE
            val response = rtfinstance().getSingleProduct("9680ead9-57f6-441d-af5f-a384a66d3300")
            tvw?.visibility = View.VISIBLE
            continueToProduct?.visibility = View.VISIBLE
            if (response.isSuccessful) {
                progressBar?.visibility = View.GONE
                val productName = response.body()?.product?.get(0)?.prodname.toString()
                if (tv1 != null) {
                    tv1.text = productName.uppercase()
                }
                if (imageR != null) {
                    largeShow(
                        response.body()?.product?.get(0)?.imageone.toString(),
                        imageR
                    )
                }
                if (imageRTwo != null) {
                    smallShow(
                        response.body()?.product?.get(0)?.imagetwo.toString(),
                        imageRTwo
                    )
                }
                if (imageRThree != null) {
                    smallShow(
                        response.body()?.product?.get(0)?.imagethree.toString(),
                        imageRThree
                    )
                }
                if (tv2 != null) {
                    tv2.text = response.body()?.product?.get(0)?.proddescr.toString()
                }
            } else {
                if (tv1 != null) {
                    tv1.text = response.errorBody()?.string().toString()
                }
            }
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("Error", it) }}}}

//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/
