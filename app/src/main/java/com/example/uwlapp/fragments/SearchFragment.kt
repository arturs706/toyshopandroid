package com.example.uwlapp.fragments

import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.uwlapp.customconfigs.customconfigs
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.R
import com.example.uwlapp.adapters.SearchAdapter
import com.example.uwlapp.databinding.FragmentSearchBinding
import com.example.uwlapp.models.ProductResponse
import com.example.uwlapp.models.Productlistres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    var newArrayList: ArrayList<ProductResponse> = ArrayList()
    var activity: Activity? = getActivity()
    var checkarray: ArrayList<Productlistres> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialising user id, getting the user id from the shared preferences, also checking if the user is logged in
        //if the user is logged in, then the user id is retrieved from the shared preferences
        //if the user is not logged in, then the user is redirected to the login page
        var userid = ""
        val userSharedPreferences = requireActivity().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        val tokentouse = userSharedPreferences.getString("USERTOKEN", "null")
        val tokendetails = userSharedPreferences.getString("USERTOKEN", null)
            ?.let { customconfigs.tokenconvert(it) }
        if (tokendetails != null) {
            userid = tokendetails.substring(8, 44)
        } else {
            customconfigs.loginredirection(requireActivity())
        }
//initialising a coroutine scope, to get the data from the api
        lifecycleScope.launchWhenCreated {
            try {
                val response = rtfinstance().getAllProducts()
                if (response.isSuccessful) {

                    binding.idRV.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    //getting the data from the api with the help of the rtfinstance
                    val responsett = tokentouse?.let { rtfinstance().getFavourites(it, userid) }
                    if (responsett != null) {
                        if (response.isSuccessful) {
                            //converting the response to a list of products for recycler view
                            checkarray = responsett.body()?.productlistres!! as ArrayList<Productlistres>
                        } else {
                            withContext(Dispatchers.Main) {
                                var toastError = responsett.errorBody()?.string().toString()
                                Toast.makeText(activity, toastError, Toast.LENGTH_SHORT).show()
                                customconfigs.loginredirection(requireActivity())
                            }}}
                    //passing the data to the array
                    val productList = response.body()?.products!! as ArrayList<ProductResponse>
                    productList.listIterator()
                    for (i in productList) {
                        for (j in checkarray) {
                            Log.e("checkarray", j.productid)
                            if (i.productid == j.productid) {
                                i.isFavorite = true
                            }}}
                    //creating a new array list to store the data and display it in the recycler view
                    //also listens for changes
                    newArrayList.addAll(productList)
                    recyclerView = binding.idRV
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    recyclerView.setHasFixedSize(true)
                    adapter = SearchAdapter(productList)
                    recyclerView.adapter = adapter
                    binding.idSV.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            //creating a new array list to store the data and display it in the recycler view
                            //also listens for changes
                            newArrayList.clear()
                            recyclerView.adapter?.notifyDataSetChanged()
                            adapter = SearchAdapter(newArrayList)
                            recyclerView.adapter = adapter
                            binding.idSV.clearFocus()
                            for (i in productList.indices) {
                                if (productList.get(i).prodname.lowercase(Locale.ROOT).contains(query.toString().lowercase(
                                        Locale.ROOT))) {
                                    newArrayList.add(productList.get(i))
                                } else {
                                    if (activity != null) {
                                        Toast.makeText(activity, "No Product Found", Toast.LENGTH_SHORT).show()
                                    }}}
                            recyclerView.adapter?.notifyDataSetChanged()
                            adapter = SearchAdapter(newArrayList)
                            recyclerView.adapter = adapter
                            return true
                        }
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onQueryTextChange(newText: String?): Boolean {
                            newArrayList.clear()
                            recyclerView.adapter?.notifyDataSetChanged()
                            val searchText = newText?.lowercase(Locale.ROOT)
                            if (searchText != null) {
                                productList.forEach {
                                    if (it.prodname.lowercase(Locale.ROOT).contains(searchText)) {
                                        newArrayList.add(it)
                                    }
                                }
                                recyclerView.adapter = SearchAdapter(newArrayList)
                            } else {
                                newArrayList.clear()
                                newArrayList.addAll(productList)
                                recyclerView.adapter = SearchAdapter(newArrayList)
                            }
                            return true
                        }})
                } else {
                    val responseerror = response.errorBody()?.string().toString()
                    Toast.makeText(activity, responseerror, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("Error", it) }
            }}}}