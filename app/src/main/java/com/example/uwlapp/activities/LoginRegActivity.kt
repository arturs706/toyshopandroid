package com.example.uwlapp.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.uwlapp.customconfigs.customconfigs.lightscreen
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.CommonicatorTwo
import com.example.uwlapp.databinding.ActivityLoginregBinding
import com.example.uwlapp.fragments.*
import java.util.*




class LoginRegActivity : AppCompatActivity(), CommonicatorTwo {

    private lateinit var binding: ActivityLoginregBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val LoginFragment = LoginFragment()

        super.onCreate(savedInstanceState)
        binding = ActivityLoginregBinding.inflate(layoutInflater)
        setContentView(binding.root)
//    ***************************************************************************************/
//    *    Title: View Binding
//    *    Author: Android Developers
//    *    Date: 24 March 2022
//    *    Availability: https://developer.android.com/topic/libraries/data-binding/expressions
//    ***************************************************************************************/

        lightscreen(this)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentContainerLogin, LoginFragment)
            commit()
        }
//    ***************************************************************************************/
//    *    Title: Redirect a fragment to another fragment-kotlin
//    *    Author: Dheeraj Prajapat
//    *    Availability: https://www.appsloveworld.com/kotlin/100/147/redirect-a-fragment-to-another-fragment
//    ***************************************************************************************/


    }
    //function for enclosing the data from one fragment to another, using the bundle

    override fun passDataCom(editTextData: ArrayList<String>) {
        val bundle = Bundle()
        bundle.putStringArrayList("message", editTextData)
        val transaction = this.supportFragmentManager.beginTransaction()
        val frag2 = RegtwoFragment()
        frag2.arguments = bundle
        transaction.replace(R.id.flFragmentContainerLogin, frag2).commit()
    }
    //function for enclosing the data from one fragment to another, using the bundle used for different fragment

    override fun passDataComTwo(editTextData: String) {
        val bundle = Bundle()
        bundle.putString("orderid", editTextData)
        val transaction = this.supportFragmentManager.beginTransaction()
        val frag2 = RegtwoFragment()
        frag2.arguments = bundle
        transaction.replace(R.id.flFragmentContainerLogin, frag2).commit()
    }
    //function for enclosing the data from one fragment to another, using the bundle used for different fragment

    override fun passDataComThree(editTextData: String) {
        val bundle = Bundle()
        val frag2 = DialogFragment()
        frag2.arguments = bundle
        bundle.putString("message", editTextData)
        frag2.show(supportFragmentManager, "TAG")
    }
}

//    ***************************************************************************************/
//    *    Title: Send data from one Fragment to another using Kotlin?
//    *    Author: Azhar from Tutorialspoint
//    *    Date: 20 April 2020
//    *    Availability: https://www.tutorialspoint.com/send-data-from-one-fragment-to-another-using-kotlin
//    ***************************************************************************************/
