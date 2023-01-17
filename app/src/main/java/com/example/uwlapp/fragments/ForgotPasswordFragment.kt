package com.example.uwlapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.Commonicator
import com.example.uwlapp.customconfigs.CommonicatorTwo
import com.example.uwlapp.databinding.FragmentForgotPasswordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.regex.Pattern


class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //redirecting to login page
        binding.tvLogin.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flFragmentContainerLogin, LoginFragment())
                ?.commit()
        }
        //listener for the edit text, checking if the email is valid or not, if valid then the button is enabled, and button color is changed
        binding.etEmail.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val result = emailVerification(binding.etEmail.text.toString())
                    if (result){
                        binding.btnRecoverpass.isEnabled = true
                        binding.btnRecoverpass.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
                        binding.btnRecoverpass.background = ContextCompat.getDrawable(
                            binding.btnRecoverpass.context,
                            R.drawable.buttonbackground
                        )
        //if the email is not valid then the button is disabled and the button color is changed
                    } else {
                        binding.btnRecoverpass.isEnabled = false
                        binding.btnRecoverpass.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorAccentDark)
                        binding.etEmail.error = "Please enter a valid email"
                    }
        //if email is valid then the button is enabled and the button color is changed, api call is made to send the email
                    binding.btnRecoverpass.setOnClickListener {
                        passwordrecover(binding.etEmail.text.toString())
                    }

                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun afterTextChanged(s: Editable) {
                }})


    }
    //function enclosing email data and sending it to the api as a json object
    private fun passwordrecover(email: String) {
        val jsonObject = JSONObject()
            .accumulate("email", email.toString())
        val requestBody = jsonObject.toString().toRequestBody(
            "application/json".toMediaTypeOrNull(),
        )
//    ***************************************************************************************/
//    *    Title: Simple POST request on Android Kotlin using Retrofit
//    *    Author: Sazzad Hissain Khan
//    *    Date: 01 April 2020
//    *    Availability: https://medium.com/swlh/simplest-post-request-on-android-kotlin-using-retrofit-e0a9db81f11a
//    ***************************************************************************************/

        //initializing a coroutine scope to make the api call
        lifecycleScope.launchWhenCreated {
            try {
                val response = rtfinstance().emailRecover(requestBody)
                //if the response is successful then the user is redirected to the login page, also a toast is displayed
                if (response.isSuccessful) {
                    val status = response.body()?.status!!
                    if (status == "success"){
                        //if the response is not successful, then display the error message on the dialog
                        val datatransf = activity as CommonicatorTwo
                        //calling the function from the commonicator interface, to display the error message
                        //on the dialog
                        datatransf.passDataComThree("Link for password recovery has been sent to your email")
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

                        delay(1000)
                        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainerLogin, LoginFragment())?.commit()
//    ***************************************************************************************/
//    *    Title: Redirect a fragment to another fragment-kotlin
//    *    Author: Dheeraj Prajapat
//    *    Availability: https://www.appsloveworld.com/kotlin/100/147/redirect-a-fragment-to-another-fragment
//    ***************************************************************************************/

                    }

                } else {
                    //if the response is not successful then a toast is displayed
                    withContext(Dispatchers.Main) {
                        var toastError = response.errorBody()?.string().toString()
                        Toast.makeText(activity, toastError, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("Error", it) }
            }}}}
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

//regex for email verification
        private fun emailVerification(email: String?): Boolean {
        val p = Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
//    ***************************************************************************************/
//    *    Title: General Email Regex (RFC 5322 Official Standard)
//    *    Author: Zenbase
//    *    Availability: https://emailregex.com/
//    ***************************************************************************************/

    val m = p.matcher(email);
            return m.matches()
        }
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

