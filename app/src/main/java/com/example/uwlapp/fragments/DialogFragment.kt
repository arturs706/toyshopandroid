package com.example.uwlapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uwlapp.customconfigs.customconfigs
import com.example.uwlapp.databinding.FragmentDialogBinding


class DialogFragment : androidx.fragment.app.DialogFragment() {

    private lateinit var binding: FragmentDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get data from another fragment via bundle
        val message = arguments?.getString("message")
//    ***************************************************************************************/
//    *    Title: Send data from one Fragment to another using Kotlin?
//    *    Author: Azhar from Tutorialspoint
//    *    Date: 20 April 2020
//    *    Availability: https://www.tutorialspoint.com/send-data-from-one-fragment-to-another-using-kotlin
//    ***************************************************************************************/

        //if not null data is displayed
        if (message != null) {
            binding.tv1.text = message
        }


    }}


