package com.example.uwlapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.customconfigs.customconfigs.supersmallShow
import com.example.myapp.models.ResponseX
import com.example.uwlapp.R


class SingleOrderAdapter (private val orderList: ArrayList<ResponseX>):

    RecyclerView.Adapter<SingleOrderAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv1: TextView = itemView.findViewById(R.id.tv1)
        val tv2: TextView = itemView.findViewById(R.id.tv2)
        val tv3: TextView = itemView.findViewById(R.id.tv3)
        val iv1: ImageView = itemView.findViewById(R.id.iv1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_single_order,
            parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = orderList[position]
        supersmallShow(currentItem.imageone, holder.iv1)
        holder.tv1.text = currentItem.prodname
        holder.tv2.text = currentItem.quantity.toString()
        holder.tv3.text = currentItem.price
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

