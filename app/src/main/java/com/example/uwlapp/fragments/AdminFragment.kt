package com.example.uwlapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.uwlapp.R
import com.example.uwlapp.databinding.FragmentAdminBinding


class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //redirect to the particular fragment
        binding.btnUserOrders.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainer, UserordersFragment())?.commit()
        }
        binding.btnUsers.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragmentContainer, CheckuserFragment())?.commit()
        }
    }}

//    ***************************************************************************************/
//    *    Title: Redirect a fragment to another fragment-kotlin
//    *    Author: Dheeraj Prajapat
//    *    Availability: https://www.appsloveworld.com/kotlin/100/147/redirect-a-fragment-to-another-fragment
//    ***************************************************************************************/

