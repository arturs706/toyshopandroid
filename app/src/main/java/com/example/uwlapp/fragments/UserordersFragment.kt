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
import com.example.uwlapp.adapters.UserordersAdapter
import com.example.uwlapp.databinding.FragmentUserordersBinding
import com.example.uwlapp.models.ResponseXX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserordersFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserordersAdapter
    private lateinit var binding: FragmentUserordersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserordersBinding.inflate(inflater, container, false)
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
        val arraylist = arrayListOf<ResponseXX>()
        lifecycleScope.launchWhenCreated {
            try {
                val response = rtfinstance().getAdminOrders(tokentouse)
                if (response.isSuccessful) {

                    val response = response.body()?.response
                    for (i in response!!) {
                        Log.e("response", i.toString())
                        arraylist.add(ResponseXX(i.created_at, i.orderid, i.total, i.fullname))
                    }

                    if (response != null) {
                        recyclerView = binding.recyclerView
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.setHasFixedSize(true)
                        adapter = UserordersAdapter(arraylist)
                        recyclerView.adapter = adapter
                    }
                } else {
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