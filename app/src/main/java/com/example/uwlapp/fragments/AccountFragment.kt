package com.example.uwlapp.fragments

import android.content.Context
import android.content.Intent
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
import com.example.uwlapp.customconfigs.customconfigs.tokenconvert
import com.example.uwlapp.customconfigs.customconfigs.loginredirection
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.R
import com.example.uwlapp.activities.SplashScreenActivity
import com.example.uwlapp.databinding.FragmentAccountBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class AccountFragment : Fragment(R.layout.fragment_account) {

        private lateinit var binding: FragmentAccountBinding
        private lateinit var userid: String

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentAccountBinding.inflate(inflater, container, false)
            return binding.root
        }


        @RequiresApi(Build.VERSION_CODES.O)
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.btnLogout.setOnClickListener{
                val intent = Intent(activity, SplashScreenActivity::class.java)
                startActivity(intent)
                //getting the shared preferences
                val userSharedPreferences = requireActivity().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
                val cartSharedPreferences = requireActivity().getSharedPreferences("CART", Context.MODE_PRIVATE)
                val qtySharedPreferences = requireActivity().getSharedPreferences("TOTALQTY", Context.MODE_PRIVATE)
                val editor = qtySharedPreferences.edit()
                userSharedPreferences.edit().clear().apply()
                cartSharedPreferences.edit().clear().apply()
                editor.putInt("TOTALQTY", 0)
                editor.apply()
            }
            val userSharedPreferences = requireActivity().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
            val tokentouse = userSharedPreferences.getString("USERTOKEN", "null")
            val tokendetails = userSharedPreferences.getString("USERTOKEN", null)

                ?.let { tokenconvert(it) }

            if (tokendetails != null) {
                userid = tokendetails.substring(8, 44)
                //remove two last characters
                val admincheck = tokendetails.substring(0, tokendetails.length - 2)
                //check if last 5 characters are admin
                val boolcheck = admincheck.endsWith("Admin")
                //if admin then particualar views are allowed
                if (boolcheck) {
                    binding.flUpdateUser.visibility = View.GONE
                    binding.flChangeAddress.visibility = View.GONE
                    binding.btnAdmin.setOnClickListener{
                        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainer, AdminFragment())?.commit()
                    }
//    ***************************************************************************************/
//    *    Title: Redirect a fragment to another fragment-kotlin
//    *    Author: Dheeraj Prajapat
//    *    Availability: https://www.appsloveworld.com/kotlin/100/147/redirect-a-fragment-to-another-fragment
//    ***************************************************************************************/

                }
                //if not admin then particualar views are gone

                if (!boolcheck) {
                    binding.flAdminPanel.visibility = View.GONE
                }
            //if not logged in then redirect to login page
            } else {
                loginredirection(requireActivity())
            }
            //initializing a coroutine scope for single user data
            lifecycleScope.launchWhenCreated {
                try {
                    val response = rtfinstance().getSingleUser(tokentouse.toString(), userid)
                    if (response.isSuccessful){
                        //if response is successful then appropriate data is displayed, for admin or user
                        val admincheck = tokendetails?.substring(0, tokendetails.length - 2)
                        binding.progressBar.visibility = View.GONE
                        binding.flChangeAddress.visibility = View.VISIBLE
                        binding.flChangePassword.visibility = View.VISIBLE
                        binding.flLogout.visibility = View.VISIBLE

                        val boolcheck = admincheck?.endsWith("Admin")
                        if (boolcheck == true) {
                            binding.flUpdateUser.visibility = View.GONE
                            binding.btnAdmin.setOnClickListener{
                                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainer, AdminFragment())?.commit()
                            }
                            binding.flAdminPanel.visibility = View.VISIBLE
                        } else {
                            binding.flUpdateUser.visibility = View.VISIBLE
                            binding.flAdminPanel.visibility = View.GONE
                        }}
                    //if not successful then appropriate error message is displayed and redirected to login fragment
                    if (!response.isSuccessful){
                        withContext(Dispatchers.Main) {
                            var toastError = response.errorBody()?.string().toString()
                            Toast.makeText(activity, toastError, Toast.LENGTH_SHORT).show()
                            loginredirection(requireActivity())
                        }
                    }
                } catch (e: Exception) {
                    e.localizedMessage?.let { Log.e("Error", it) }
                }}
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

            //redirecting to particular fragments to change user details
            binding.ivUpdateAcc.setOnClickListener {
                getActivity()?.getSupportFragmentManager()?.beginTransaction()?.replace(R.id.flFragmentContainer, UpdateProfileFragment())?.commit()
            }
            binding.ivUpdateAddress.setOnClickListener{
                getActivity()?.getSupportFragmentManager()?.beginTransaction()?.replace(R.id.flFragmentContainer, UpdateaddressFragment())?.commit()
            }
            binding.ivUpdatePassword.setOnClickListener{
                getActivity()?.getSupportFragmentManager()?.beginTransaction()?.replace(R.id.flFragmentContainer, UpdatePasswordFragment())?.commit()
            }}}

//    ***************************************************************************************/
//    *    Title: Redirect a fragment to another fragment-kotlin
//    *    Author: Dheeraj Prajapat
//    *    Availability: https://www.appsloveworld.com/kotlin/100/147/redirect-a-fragment-to-another-fragment
//    ***************************************************************************************/


