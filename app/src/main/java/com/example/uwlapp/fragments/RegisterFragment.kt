package com.example.uwlapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.uwlapp.customconfigs.customconfigs.validateFirst
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.CommonicatorTwo
import com.example.uwlapp.databinding.FragmentRegisterBinding
import com.google.gson.Gson


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userSharedPreferences = requireActivity().getSharedPreferences("USERREG", Context.MODE_PRIVATE)
        val gson = Gson()

        val editor = userSharedPreferences.edit()
        //check if the user is already tried to register
        if (userSharedPreferences.contains("USERREG")) {
            //retrieve the user provided details
            val userHM = userSharedPreferences.getString("USERREG", null)
            var getHashMap = gson.fromJson(userHM, HashMap::class.java) as HashMap<String, String>
//    ***************************************************************************************/
//    *    Title: Gson
//    *    Author: Google/Gson
//    *    Date: 25 October 2022
//    *    Code version: 2.1.0
//    *    Availability: https://github.com/google/gson
//    ***************************************************************************************/

            //set the user provided details to the edit text
            binding.etFullName.setText(getHashMap["fullname"])
            binding.etUsername.setText(getHashMap["username"])
            binding.etMobile.setText(getHashMap["mobile"])
            binding.etEmail.setText(getHashMap["email"])
            binding.etPasswordOne.setText(getHashMap["passwordOne"])
            binding.etPasswordTwo.setText(getHashMap["passwordTwo"])
        }

        //redirect to login page
        binding.tvLogin.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flFragmentContainerLogin, LoginFragment())
                ?.commit()
        }
//    ***************************************************************************************/
//    *    Title: Redirect a fragment to another fragment-kotlin
//    *    Author: Dheeraj Prajapat
//    *    Availability: https://www.appsloveworld.com/kotlin/100/147/redirect-a-fragment-to-another-fragment
//    ***************************************************************************************/

        //initialise the commonicator, which is used to pass data between fragments
        //new array is also created so that the data can be passed to the next fragment
        val arrayList = ArrayList<String>()
        validateFirst(binding.etFullName, binding.etUsername, binding.etMobile, binding.etEmail, binding.etPasswordOne, binding.etPasswordTwo, binding.btnNextsreg)
        binding.btnNextsreg.setOnClickListener {
            //create a HashMap to store the data from the edit text to be kept in the shared preferences
            val getHashMap = HashMap<String, String>()
            getHashMap["fullname"] = binding.etFullName.text.toString()
            getHashMap["username"] = binding.etUsername.text.toString()
            getHashMap["mobile"] = binding.etMobile.text.toString()
            getHashMap["email"] = binding.etEmail.text.toString()
            getHashMap["passwordOne"] = binding.etPasswordOne.text.toString()
            getHashMap["passwordTwo"] = binding.etPasswordTwo.text.toString()
            //convert the HashMap to a string using Gson
            val json: String = gson.toJson(getHashMap)
            //store the string in the shared preferences
            editor.putString("USERREG", json)
            editor.apply()
            //pass the data to the next fragment
            val commonicator = activity as CommonicatorTwo
            arrayList.add(binding.etFullName.text.toString())
            arrayList.add(binding.etUsername.text.toString())
            arrayList.add(binding.etMobile.text.toString())
            arrayList.add(binding.etEmail.text.toString())
            arrayList.add(binding.etPasswordOne.text.toString())
            arrayList.add(binding.etPasswordTwo.text.toString())
            commonicator.passDataCom(arrayList)

        }}}

