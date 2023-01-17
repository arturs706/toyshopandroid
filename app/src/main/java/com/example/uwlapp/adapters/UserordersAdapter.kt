package com.example.uwlapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.Commonicator
import com.example.uwlapp.models.ResponseXX


class UserordersAdapter (private val orderList: ArrayList<ResponseXX>):

    RecyclerView.Adapter<UserordersAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderNumber: TextView = itemView.findViewById(R.id.tvOrderTwo)
        val Fullname: TextView = itemView.findViewById(R.id.tvFullname)
        val checkOrder: Button = itemView.findViewById(R.id.checkOrder)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.userorders,
            parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = orderList[position]
        holder.orderNumber.text = currentItem.orderid.toString()
        holder.Fullname.text = currentItem.fullname
        holder.checkOrder.setOnClickListener {
            val datatransf = holder.itemView.context as Commonicator
            datatransf.passDataCom(currentItem.orderid.toString())
//    ***************************************************************************************/
//    *    Title: Send data from one Fragment to another using Kotlin?
//    *    Author: Azhar from Tutorialspoint
//    *    Date: 20 April 2020
//    *    Availability: https://www.tutorialspoint.com/send-data-from-one-fragment-to-another-using-kotlin
//    ***************************************************************************************/

        }
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

