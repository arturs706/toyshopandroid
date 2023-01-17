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
import com.example.uwlapp.adapters.CheckuserAdapter
import com.example.uwlapp.databinding.FragmentCheckuserBinding
import com.example.uwlapp.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CheckuserFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CheckuserAdapter
    private lateinit var binding: FragmentCheckuserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckuserBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userSharedPreferences = requireActivity().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        val tokentouse = userSharedPreferences.getString("USERTOKEN", "null")
        getOrders(tokentouse.toString())

    }
    fun getOrders (tokentouse: String) {
        //arraylist to store the single user
        val arraylist = arrayListOf<Users>()
        //instantiating the CoroutineScope
        lifecycleScope.launchWhenCreated {
            try {
                //getting the response from the api, retrieving all user data
                val response = rtfinstance().getAllUsers(tokentouse)
                if (response.isSuccessful) {

                    val response = response.body()?.users!!
                    for (i in response!!) {
                        //adding the user data to the arraylist
                        arraylist.add(Users(i.usid, i.fullname, i.username, i.dob, i.gender, i.mob_phone, i.email, i.address, i.city, i.postcode, i.created_at))
                    }

                    if (response != null) {
                        //adding the user data to the recyclerview and displaying it

                        recyclerView = binding.recyclerView
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.setHasFixedSize(true)
                        adapter = CheckuserAdapter(arraylist)
                        recyclerView.adapter = adapter
                    }
                } else {
                    //if the response is not successful, redirect to the login page, also show a toast message
                    withContext(Dispatchers.Main) {
                        val toastError = response.errorBody()?.string().toString()
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