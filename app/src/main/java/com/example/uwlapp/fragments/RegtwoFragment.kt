package com.example.uwlapp.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.MainActivity
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.CommonicatorTwo
import com.example.uwlapp.customconfigs.postcodeValidationOnTxtChange
import com.example.uwlapp.databinding.FragmentRegtwoBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.*
import java.util.regex.Pattern


class RegtwoFragment : Fragment() {
    private lateinit var binding: FragmentRegtwoBinding
    private lateinit var etdob: TextInputEditText
    private lateinit var etaddress: TextInputEditText
    private lateinit var etcity: Spinner
    private lateinit var etpostcode: TextInputEditText
    private lateinit var gender: String
    //initializing the variables for the date picker
    var day = 0
    var month = 0
    var year = 0
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegtwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        //pickDate function
        pickDate()
        //login button listener
        binding.tvLogin.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flFragmentContainerLogin, LoginFragment())
                ?.commit()
        }
        //assigning the variables to the edit text and text view
        etdob = binding.etdob
        etaddress = binding.etaddress
        etcity = binding.etcity
        etpostcode = binding.etpostcode
        //getting data passed from the previous fragment with the help of bundle
        val regOneValues = arguments?.getStringArrayList("message")
        //listener for the register button, checking if the email and password are valid or not,
        ontextchangeRadio(binding.etSpecify, binding.rg1)
        //button listener for the register button
        binding.btnRegComplete.setOnClickListener { regUser(regOneValues) }
        //if address is empty, error message is shown
        if(etaddress.text.toString().isEmpty()){
            etaddress.error = "Address is required"
        }
        //radio group and its listener, for assigning gender
        val radioGroup = binding.rg1
        gender = selectVariant()
        radioGroup.setOnCheckedChangeListener { _, _ ->
            gender = selectVariant()
        }
        //selecting the city from the spinner
        etcity.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {}
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {
                binding.btnRegComplete.isEnabled = false
            }}
        //listener for the postcode, that also validates the postcode with the help of regex
        postcodeValidationOnTxtChange(etpostcode)
    }
    //listener for the radio group
    private fun ontextchangeRadio(textInputEditText: TextInputEditText, radioGroup: RadioGroup) {
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (textInputEditText.text.toString().isNotEmpty()) {
                    radioGroup.clearCheck()
                    radioGroup.isVisible = false
                    gender = textInputEditText.text.toString()
                } else {radioGroup.isVisible = true}}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
            override fun afterTextChanged(s: Editable) {}})}
//calenar instance for the date picker for the date of birth
    @SuppressLint("SetTextI18n")
    private fun pickDate() {
        val c = Calendar.getInstance()
        day = c.get(Calendar.DAY_OF_MONTH)
        month = c.get(Calendar.MONTH)
        year = c.get(Calendar.YEAR)
        binding.etdob.setOnClickListener {
            val datepickerInst = DatePickerDialog(
                requireContext(),
                R.style.datepicker,
                { _, year, monthOfYear, dayOfMonth ->
                    savedDay = dayOfMonth
                    savedMonth = monthOfYear
                    savedYear = year
                    etdob.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                },year,month,day)
            datepickerInst.show()}}
//returns chosen value
private fun selectVariant(): String {
    val radioGroup = binding.rg1
    when (radioGroup.checkedRadioButtonId) {
        R.id.rb1 -> return "Male"
        R.id.rb2 -> return "Female"
        else -> return "specify"
    }}
//function for registering the user
    private fun regUser(regOneValues: ArrayList<String>?) {
    //adding the values to the array list
        regOneValues?.add(gender)
        regOneValues?.add(etdob.text.toString())
        regOneValues?.add(etaddress.text.toString())
        regOneValues?.add(etcity.selectedItem.toString())
        regOneValues?.add(etpostcode.text.toString())
//attaching the values to the Json object
        val jsonObject = JSONObject()
            .accumulate("fullname", regOneValues?.get(0))
            .accumulate("username", regOneValues?.get(1))
            .accumulate("dob", regOneValues?.get(7))
            .accumulate("gender", regOneValues?.get(6))
            .accumulate("mob_phone", regOneValues?.get(2))
            .accumulate("email", regOneValues?.get(3))
            .accumulate("passwd", regOneValues?.get(5))
            .accumulate("address", regOneValues?.get(8))
            .accumulate("city", regOneValues?.get(9))
            .accumulate("postcode", regOneValues?.get(10))

//converting the strings to json object
        val requestBody = jsonObject.toString().toRequestBody(
            "application/json".toMediaTypeOrNull(),
        )
    //calling the api passing the json object
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = rtfinstance().registerUser(requestBody)
                if (response.isSuccessful) {
                    //if the response is successful, the user is redirected to MainActivity and values are deleted from the shared preferences
                    val sharedPref = requireActivity().getSharedPreferences("USERREG", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()
                    val datatransf = activity as CommonicatorTwo
                    datatransf.passDataComThree("Registration Successful")
                    DialogFragment().showsDialog
                    //delay for 2 seconds to show the dialog before redirecting to the main activity
                    delay(2000)
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    //if the response is unsuccessful, the user is redirected to login page
                        //if the response is not successful, then display the error message on the dialog
                        val datatransf = activity as CommonicatorTwo
                        //calling the function from the commonicator interface, to display the error message
                        //on the dialog
                        //the error message is passed as a parameter
                        val responseBody = response.errorBody()?.string()
                        //extracting the error message from the response body
                        val jsonObject = JSONObject(responseBody)
                        val message = jsonObject.getString("message")
                        datatransf.passDataComThree(message)
                        DialogFragment().showsDialog
                        //redirecting the user to the registration fragment
                        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainerLogin, RegisterFragment())?.commit()
                }} catch (e: Exception) {
                e.localizedMessage?.let { Log.e("Error", it) }
            }}}}





