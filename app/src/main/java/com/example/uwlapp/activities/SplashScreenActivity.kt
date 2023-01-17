package com.example.uwlapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.uwlapp.customconfigs.customconfigs
import com.example.uwlapp.MainActivity
import com.example.uwlapp.api.ApiCalls
import com.example.uwlapp.databinding.ActivitySplashScreenBinding


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
//    ***************************************************************************************/
//    *    Title: View Binding
//    *    Author: Android Developers
//    *    Date: 24 March 2022
//    *    Availability: https://developer.android.com/topic/libraries/data-binding/expressions
//    ***************************************************************************************/

        //remove the action bar and navigation bar from the splash screen
        customconfigs.lightscreen(this)

        //preload data and cache it in memory

        lifecycleScope.launchWhenCreated {
            ApiCalls.productList(null, null, null, null, null, null, null, null)
    }
//    ***************************************************************************************/
//    *    Title: Lifecycle-aware coroutine scopes
//    *    Author: Android Developers
//    *    Availability: https://developer.android.com/topic/libraries/architecture/coroutines
//    ***************************************************************************************/


        //start the main activity
        binding.btnGetStartedReg.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
}
}