package com.example.uwlapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.uwlapp.R
import com.example.uwlapp.databinding.FragmentContactusBinding


class ContactusFragment : Fragment() {

    private lateinit var binding: FragmentContactusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //redirect to the particular fragment, displaying privacy policy
        binding.btnPrivacy.setOnClickListener {
            getActivity()?.getSupportFragmentManager()?.beginTransaction()?.replace(R.id.flFragmentContainer, PrivacyFragment())?.commit()
        }
        //redirect to the call intent when the call button is clicked
        binding.btnCall.setOnClickListener {
            //make a call to the number on the button click
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel: 02072444444")
            startActivity(intent)
        }
        //redirect to the email intent when the email button is clicked
        binding.btnEmailUs.setOnClickListener {
            //send an email to the email address on the button click
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_EMAIL, "21437262@student.uwl.ac.uk")
            intent.putExtra(Intent.EXTRA_SUBJECT, "UWL App")
            intent.putExtra(Intent.EXTRA_TEXT, "Hi, I have a question about the UWL App")
            startActivity(Intent.createChooser(intent, "Send Email"))
        }
// ***************************************************************************************/
//    *    Title: How to send email in android using intent
//    *    Author: JavaTPoint
//    *    Availability: https://www.javatpoint.com/how-to-send-email-in-android-using-intent
//    ***************************************************************************************/

}}


