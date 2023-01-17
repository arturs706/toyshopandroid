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
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.*
import com.example.uwlapp.databinding.FragmentUpdateProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class UpdateProfileFragment : Fragment() {

    private lateinit var binding: FragmentUpdateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
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
        //listens for updates and errors are displayed if any field is not validated
        mobileValidationOnTextChange(binding.etMobilePhone)
        emailValidationOnTextChange(binding.etEmailAddress)
        passwordValidationOnTextChange(binding.etConfirmPass1)
        passwordValidationOnTextChange(binding.etConfirmPass2)
        binding.btnUpdateProfile.setOnClickListener {
            if ((binding.etConfirmPass1.text.toString() == binding.etConfirmPass2.text.toString())) {
                updateProfile(tokentouse.toString(), tokendetails.toString())
            } else {
                val datatransf = activity as Commonicator
                //calling the function from the commonicator interface, to display the error message
                //on the dialog
                datatransf.passDataComFive("Passwords do not match")
                DialogFragment().showsDialog
            }
        }
}

    fun updateProfile(tokentouse: String, tokendetails: String){
        lifecycleScope.launchWhenCreated {
            val jsonObject = JSONObject()
                .accumulate("fullname", binding.etFullName.text.toString())
                .accumulate("username", binding.etUserName.text.toString())
                .accumulate("mob_phone", binding.etMobilePhone.text.toString())
                .accumulate("email", binding.etEmailAddress.text.toString())
                .accumulate("passwd", binding.etConfirmPass2.text.toString())
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
                val response = customconfigs.rtfinstance().updateUser(tokentouse.toString(), tokendetails.toString(), requestBody)
                if (response.isSuccessful) {
                    val resp = response.body()?.status
                    if (resp == "success"){
                        //if the response is not successful, then display the error message on the dialog
                        val datatransf = activity as Commonicator
                        //calling the function from the commonicator interface, to display the error message
                        //on the dialog
                        datatransf.passDataComFive("Profile Updated Successfully")

                        DialogFragment().showsDialog
                        delay(1000)
                        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainer, AccountFragment())?.commit()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        //extracting the error message from the response
                        val error = JSONObject(response.errorBody()?.string())
                        val errormessage = error.getString("message")
                        //if the response is not successful, then display the error message on the dialog
                        val datatransf = activity as Commonicator
                        //calling the function from the commonicator interface, to display the error message
                        //on the dialog
                        datatransf.passDataComFive(errormessage)
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

    }}