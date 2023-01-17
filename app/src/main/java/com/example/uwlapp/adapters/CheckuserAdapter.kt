package com.example.uwlapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.R
import com.example.uwlapp.models.Users


class CheckuserAdapter (private val orderList: ArrayList<Users>):

    RecyclerView.Adapter<CheckuserAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullName: TextView = itemView.findViewById(R.id.tvFullname)
        val userName: TextView = itemView.findViewById(R.id.tvUsername)
        val mobilePhone: TextView = itemView.findViewById(R.id.tvMobilephone)
        val email: TextView = itemView.findViewById(R.id.tvEmail)
        val address: TextView = itemView.findViewById(R.id.tvAddress)
        val city: TextView = itemView.findViewById(R.id.tvCity)
        val postcode: TextView = itemView.findViewById(R.id.tvPostcode)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.checkusersadmin,
            parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = orderList[position]
        holder.fullName.text = currentItem.fullname
        holder.userName.text = currentItem.username
        holder.mobilePhone.text = currentItem.mob_phone
        holder.email.text = currentItem.email
        holder.address.text = currentItem.address
        holder.city.text = currentItem.city
        holder.postcode.text = currentItem.postcode


    }
    override fun getItemCount(): Int {
        return orderList.size
    }
}
//    ***************************************************************************************/
//    *    Title: Android: RecyclerView Adapters with Kotlin
//    *    Author: Moksh Jain
//    *    Date: 13 July 2017
//    *    Availability: https://medium.com/@jainmoksh/android-recyclerview-adapters-with-kotlin-ab3e9c8af4fa
//    ***************************************************************************************/
