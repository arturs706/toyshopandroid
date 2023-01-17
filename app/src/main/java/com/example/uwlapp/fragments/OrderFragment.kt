package com.example.uwlapp.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.customconfigs.customconfigs.loginredirection
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.customconfigs.customconfigs.tokenconvert
import com.example.myapp.models.ReceivedOrderResponse
import com.example.uwlapp.adapters.OrdersAdapter
import com.example.uwlapp.databinding.FragmentOrderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class OrderFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrdersAdapter
    private lateinit var binding: FragmentOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //retrieving JWT token from shared preferences, so that it can be used to make the api call
        val userSharedPreferences = requireActivity().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        val tokentouse = userSharedPreferences.getString("USERTOKEN", "null")
        val tokendetails = userSharedPreferences.getString("USERTOKEN", null)
            ?.let { tokenconvert(it) }
        val orderiteminc = tokendetails?.substring(8, 44)
        //function to get the orders of the logged in user from the database, passing the user id and the token as parameters
        getOrders(tokentouse.toString(), orderiteminc.toString())
    }
    //function to get the orders of the logged in user from the database, passing the user id and the token as parameters
    fun getOrders (tokentouse: String, orderiteminc: String) {
        //creating arraylist to store the orders
        val arraylist = arrayListOf<ReceivedOrderResponse>()
        //initializing the CoroutineScope to make the api call
        lifecycleScope.launchWhenCreated {
            try {
                val response = rtfinstance().getOrders(tokentouse, orderiteminc)
                if (response.isSuccessful) {
                    //if the response is successful recyclerview is visible
                    binding.recyclerView.visibility = View.VISIBLE
                    val response = response.body()?.response
                    for (i in response!!) {
                        //adding the orders to the arraylist
                        val orderiteminc = i.toString().substring(17, i.toString().length - 1)
                        arraylist.add(ReceivedOrderResponse(orderiteminc))
                    }
                    //setting the adapter to the recyclerview
                    if (response != null) {
                        recyclerView = binding.recyclerView
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.setHasFixedSize(true)
                        adapter = OrdersAdapter(arraylist)
                        recyclerView.adapter = adapter
                    }
                } else {
                    //if the response is not successful, the user is redirected to the login page
                    withContext(Dispatchers.Main) {
                        var toastError = response.errorBody()?.string().toString()
                        Toast.makeText(activity, toastError, Toast.LENGTH_SHORT).show()
                        loginredirection(requireActivity())
                    }}
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("Error ->", it) }
            }
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

        }}
}