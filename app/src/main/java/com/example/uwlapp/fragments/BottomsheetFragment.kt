package com.example.uwlapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uwlapp.customconfigs.customconfigs.supersmallShow
import com.example.uwlapp.R
import com.example.uwlapp.databinding.FragmentBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomsheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// your app theme here
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.BottomSheetDialogTheme)
        val view = inflater.cloneInContext(contextThemeWrapper).inflate(R.layout.fragment_bottomsheet, container, false)
        binding = FragmentBottomsheetBinding.bind(view)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the data from the bundle using the tag
        var proddet = arguments?.getStringArrayList("itemdata")
//    ***************************************************************************************/
//    *    Title: Send data from one Fragment to another using Kotlin?
//    *    Author: Azhar from Tutorialspoint
//    *    Date: 20 April 2020
//    *    Availability: https://www.tutorialspoint.com/send-data-from-one-fragment-to-another-using-kotlin
//    ***************************************************************************************/

        if (proddet != null) {
            //display the data in the bottom sheet
            binding.tvQty.text = "Item quantity in cart: ${proddet.get(0).toString()}"
            binding.tvProductName.text = proddet.get(1).toString()
            supersmallShow(proddet.get(2).toString(), binding.ivImage)
            binding.tvPrice.text = "Item price: £${proddet.get(3).toString()}"
            binding.tvTotal.text = "Total to pay: ${proddet.get(4).toString()}"

        }}}