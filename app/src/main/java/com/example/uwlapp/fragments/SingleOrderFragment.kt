package com.example.uwlapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.myapp.models.ResponseX
import com.example.uwlapp.adapters.SingleOrderAdapter
import com.example.uwlapp.databinding.FragmentSingleorderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SingleOrderFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SingleOrderAdapter
    private lateinit var binding: FragmentSingleorderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleorderBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userSharedPreferences = requireActivity().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        val ordernr = arguments?.getString("data")
        val tokentouse = userSharedPreferences.getString("USERTOKEN", "null")
        getOrders(tokentouse.toString(), ordernr.toString())

    }
    fun getOrders (tokentouse: String, orderiteminc: String) {
        lifecycleScope.launchWhenCreated {
            try {
                val response = rtfinstance().getSingleOrder(tokentouse, orderiteminc)
                if (response.isSuccessful) {
                    var resp = response.body()?.response!! as ArrayList<ResponseX>
                    recyclerView = binding.rvSingleOrders
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    recyclerView.setHasFixedSize(true)
                    adapter = SingleOrderAdapter(resp)
                    recyclerView.adapter = adapter
                } else {
                    withContext(Dispatchers.Main) {
                        var toastError = response.errorBody()?.string().toString()
                        Toast.makeText(activity, toastError, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("Error ->", it) }
            }}}
    //    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

}