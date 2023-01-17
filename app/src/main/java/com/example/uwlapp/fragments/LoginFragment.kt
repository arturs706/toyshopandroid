package com.example.uwlapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.uwlapp.customconfigs.customconfigs
import com.example.uwlapp.customconfigs.customconfigs.textList
import com.example.myapp.api.RtfClient
import com.example.uwlapp.MainActivity
import com.example.uwlapp.R
import com.example.uwlapp.api.ApiInterface
import com.example.uwlapp.customconfigs.Commonicator
import com.example.uwlapp.customconfigs.CommonicatorTwo
import com.example.uwlapp.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*


class LoginFragment : Fragment() {
    lateinit var loginEmail: TextInputEditText
    lateinit var loginPassword: TextInputEditText
    lateinit var tv4: TextView
    lateinit var tokenString: String
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //navigation to forgotpass fragment
        binding.tvForgotPassword.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flFragmentContainerLogin, ForgotPasswordFragment())
                ?.commit()
        }
//    ***************************************************************************************/
//    *    Title: Redirect a fragment to another fragment-kotlin
//    *    Author: Dheeraj Prajapat
//    *    Availability: https://www.appsloveworld.com/kotlin/100/147/redirect-a-fragment-to-another-fragment
//    ***************************************************************************************/

        //assigning the variables to the edit text and text view
        loginEmail = binding.loginEmail
        loginPassword = binding.loginPassword
        //listener for the login button, checking if the email and password are valid or not, if valid then login is possible
        textList(loginEmail,loginPassword, binding.btnGetStartedReg)


        tv4 = binding.tv4
        //on click listener for the login function
        binding.btnGetStartedReg.setOnClickListener { loginUser() }
        //redirecting to register page
        tv4.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainerLogin, RegisterFragment())?.commit()
        }
//    ***************************************************************************************/
//    *    Title: Redirect a fragment to another fragment-kotlin
//    *    Author: Dheeraj Prajapat
//    *    Availability: https://www.appsloveworld.com/kotlin/100/147/redirect-a-fragment-to-another-fragment
//    ***************************************************************************************/

    }
    //login function
    @RequiresApi(Build.VERSION_CODES.O)
    fun loginUser() {
        //initializing the api interface, and the json object for current password data
        val retrofit = RtfClient.getInstance()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        val jsonObject = JSONObject().accumulate("email", loginEmail.text.toString())
            .accumulate("passwd", loginPassword.text.toString())
        val requestBody = jsonObject.toString().toRequestBody(
            "application/json".toMediaTypeOrNull(),
        )
//    ***************************************************************************************/
//    *    Title: Simple POST request on Android Kotlin using Retrofit
//    *    Author: Sazzad Hissain Khan
//    *    Date: 01 April 2020
//    *    Availability: https://medium.com/swlh/simplest-post-request-on-android-kotlin-using-retrofit-e0a9db81f11a
//    ***************************************************************************************/

        //calling the api interface for login
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //getting the response from the api interface, and storing the token in the shared preferences
                val response = apiInterface.loginUser(requestBody)
                if (response.isSuccessful) {
                    val userSharedPreferences = requireActivity().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
                    val token = response.headers()["Authorization"]!!.toString()
                    tokenString = "Bearer $token"
                    userSharedPreferences.edit().putString("USERTOKEN", tokenString).apply()
                    //function to convert the JWT token to base64, so that the user data can be extracted
                    val chunks = token!!.split("\\.".toRegex()).toList()
                    val decoder: Base64.Decoder = Base64.getUrlDecoder()
//    ***************************************************************************************/
//    *    Title: Kotlin Base64 Encoding and Decoding
//    *    Author: bezkoder
//    *    Date: 23 February 2020
//    *    Availability: https://www.bezkoder.com/kotlin-base64/
//    ***************************************************************************************/

                    val admincheck = (customconfigs.substring(String(decoder.decode(chunks[1])), 7))?.dropLast(2)
                    //if user is admin, then redirect to account page, else redirect to Main Activity
                    if (admincheck == "Admin") {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("FRAGMENT", "account")
                        startActivity(intent)
                    } else {
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    //if the response is not successful, then display the error message on the dialog
                    val datatransf = activity as CommonicatorTwo
                    //calling the function from the commonicator interface, to display the error message
                    //on the dialog
                    datatransf.passDataComThree("Invalid Credentials")
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
            } catch (e: Exception) {
                Log.e("Error",e.localizedMessage)
            }
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

        }}}




