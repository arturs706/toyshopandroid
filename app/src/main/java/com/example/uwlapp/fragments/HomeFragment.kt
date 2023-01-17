package com.example.uwlapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.uwlapp.R
import com.example.uwlapp.api.ApiCalls.productList
import com.example.uwlapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

     private lateinit var binding: FragmentHomeBinding

     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         binding = FragmentHomeBinding.inflate(inflater, container, false)
         return binding.root
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

         lifecycleScope.launchWhenCreated {
             // if the response is successful, then data is retrieved

             productList(
                 binding.continueToProduct,
                         binding.tvWhatsNew,
                binding.tv1,
                binding.tv2,
                binding.iv1,
                binding.iv2,
                binding.iv3,
                 binding.progressBar
            )
         }
         binding.continueToProduct.setOnClickListener {
             activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainer, ProductFragment())?.commit()
         }
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

     }}