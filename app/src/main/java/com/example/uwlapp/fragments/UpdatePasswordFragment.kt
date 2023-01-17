package com.example.uwlapp.fragments

import android.annotation.SuppressLint
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
import com.example.uwlapp.customconfigs.customconfigs
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.passwordValidationOnTextChange
import com.example.uwlapp.databinding.FragmentUpdatePasswordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*


class UpdatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentUpdatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userSharedPreferences = requireActivity().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        val tokentouse = userSharedPreferences.getString("USERTOKEN", "null")
        val tokendetails = tokentouse?.substring(7)
        if (tokendetails == null) {
            customconfigs.loginredirection(requireActivity())
        }
        //password validation
        passwordValidationOnTextChange(binding.etCurrentPassword1)
        passwordValidationOnTextChange(binding.etCurrentPassword2)
        passwordValidationOnTextChange(binding.etNewPassword)
        passwordValidationOnTextChange(binding.etConfirmNewPass)

        //check if passwords are the same
        binding.btnUpdatePass.setOnClickListener {
                if ((binding.etCurrentPassword1.text.toString() == binding.etCurrentPassword2.text.toString()) || (binding.etNewPassword.text.toString() == binding.etConfirmNewPass.text.toString())) {
                    updateUserPassword(tokentouse.toString(), tokendetails.toString())
                } else {
                    Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
    fun updateUserPassword(tokentouse: String, tokendetails: String) {
        lifecycleScope.launchWhenCreated {
            val jsonObject = JSONObject()
                .accumulate("passwd", binding.etCurrentPassword2.text.toString())
                .accumulate("newpasswd", binding.etConfirmNewPass.text.toString())
            val requestBody = jsonObject.toString().toRequestBody(
                "application/json".toMediaTypeOrNull(),
            )
//    ***************************************************************************************/
//    *    Title: Simple POST request on Android Kotlin using Retrofit
//    *    Author: Sazzad Hissain Khan
//    *    Date: 01 April 2020
//    *    Availability: https://medium.com/swlh/simplest-post-request-on-android-kotlin-using-retrofit-e0a9db81f11a
//    ***************************************************************************************/

            try {
                val response = customconfigs.rtfinstance().updateUserPassword(tokentouse, tokendetails, requestBody)
                if (response.isSuccessful) {

                    val resp = response.body()?.status
                    if (resp == "Success"){
                        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainer, AccountFragment())?.commit()
                        Toast.makeText(requireContext(), "Password Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                    //    ***************************************************************************************/
//    *    Title: Redirect a fragment to another fragment-kotlin
//    *    Author: Dheeraj Prajapat
//    *    Availability: https://www.appsloveworld.com/kotlin/100/147/redirect-a-fragment-to-another-fragment
//    ***************************************************************************************/

                } else {
                    withContext(Dispatchers.Main) {
                        var toastError = response.errorBody()?.string().toString()
                        Toast.makeText(activity, toastError, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("Error ->", it) }
            }}
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

    }
}

