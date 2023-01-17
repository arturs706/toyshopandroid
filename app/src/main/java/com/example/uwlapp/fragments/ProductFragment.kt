package com.example.uwlapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.models.ProductResponse
import com.example.uwlapp.adapters.MyAdapter
import com.example.uwlapp.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<ProductResponse>
    private lateinit var adapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        //calling the function to get the data, if the data is retrieved, the function will call the adapter

        binding.recyclerView.visibility = View.GONE
        lifecycleScope.launchWhenCreated {
            try {
                val response = rtfinstance().getAllProducts()
                //checking if the response is successful, if so, progressbar will be gone, views will be available and the data will be displayed
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    val productList = response.body()?.products!! as ArrayList<ProductResponse>
                    val itr = productList.listIterator()
                    recyclerView = binding.recyclerView
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    recyclerView.setHasFixedSize(true)
                    adapter = MyAdapter(productList)
                    recyclerView.adapter = adapter
                } else {
                    val responseerror = response.errorBody()?.string().toString()
                    Toast.makeText(activity, responseerror, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("Error", it) }
            }
        }
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/


    }


    }
