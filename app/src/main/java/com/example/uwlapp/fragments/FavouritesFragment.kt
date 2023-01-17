package com.example.uwlapp.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.customconfigs.customconfigs
import com.example.uwlapp.adapters.FavitemAdapter
import com.example.uwlapp.customconfigs.Commonicator
import com.example.uwlapp.databinding.FragmentFavouritesBinding
import com.example.uwlapp.models.Productlistres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class FavouritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var adapter: FavitemAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var userid: String = ""
        super.onViewCreated(view, savedInstanceState)
        //getting the user id from the shared preferences, also checking if the user is logged in
        val userSharedPreferences = requireActivity().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        val tokentouse = userSharedPreferences.getString("USERTOKEN", "null")
        val tokendetails = userSharedPreferences.getString("USERTOKEN", null)
            ?.let { customconfigs.tokenconvert(it) }
        //if the user is logged in, then the user id is retrieved from the shared preferences
        if (tokendetails != null) {
            userid = tokendetails.substring(8, 44)
        }
        getFavItems(tokentouse.toString(), userid.toString())
    }
    //function to get the favourite items of the logged in user from the database, passing the user id and the token as parameters
    fun getFavItems (tokentouse: String, userid: String) {
        //instantiating the CoroutineScope for the network call
        lifecycleScope.launchWhenCreated {
            try {
                //getting the response from the api, retrieving all the favourite items of the user
                val response = customconfigs.rtfinstance().getFavourites(tokentouse, userid)
                //checking if the response is successful, if so, then the favourite items are displayed in the recycler view
                if (response.isSuccessful) {
                    binding.idRV.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    val response = response.body()?.productlistres!! as ArrayList<Productlistres>
                    if (response != null) {
                        recyclerView = binding.idRV
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        recyclerView.setHasFixedSize(true)
                        adapter = FavitemAdapter(response)
                        recyclerView.adapter = adapter
                    }
                } else {
                        binding.progressBar.visibility = View.GONE
                    //if the response is not successful, then the user is redirected to the login page
                        withContext(Dispatchers.Main) {
                        //if the response is not successful, then display the error message on the dialog
                        val datatransf = activity as Commonicator
                        //calling the function from the commonicator interface, to display the error message
                        //on the dialog
                        datatransf.passDataComFive("Please login first")
//    ***************************************************************************************/
//    *    Title: Send data from one Fragment to another using Kotlin?
//    *    Author: Azhar from Tutorialspoint
//    *    Date: 20 April 2020
//    *    Availability: https://www.tutorialspoint.com/send-data-from-one-fragment-to-another-using-kotlin
//    ***************************************************************************************/

                        DialogFragment().showsDialog
//    ***************************************************************************************/
//    *    Title: How to use DialogFragment in Android
//    *    Author: Hüseyin Özkoç
//    *    Date: 16 September 2021
//    *    Availability: https://medium.com/@huseyinozkoc/how-to-use-dialogfragment-in-android-f8095dd0993f
//    *
//    ***************************************************************************************/

                            delay(2000)
                        //if the user is not logged in, then the user is redirected to the login page
                        customconfigs.loginredirection(requireActivity())
                    }
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("Error ->", it) }
            }

        }}
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

}