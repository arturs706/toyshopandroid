package com.example.uwlapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.models.ReceivedOrderResponse
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.Commonicator


class OrdersAdapter (private val orderList: ArrayList<ReceivedOrderResponse>):

    RecyclerView.Adapter<OrdersAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonClick: Button = itemView.findViewById(R.id.checkOrder)
        val orderNumber: TextView = itemView.findViewById(R.id.orderNumber)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.orderlist,
            parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = orderList[position]
        holder.orderNumber.text = currentItem.response
        holder.buttonClick.setOnClickListener {
            val datatransf = holder.itemView.context as Commonicator
            datatransf.passDataCom(currentItem.response)
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


