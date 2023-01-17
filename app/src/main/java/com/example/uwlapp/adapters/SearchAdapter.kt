package com.example.uwlapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.customconfigs.customconfigs
import com.example.uwlapp.customconfigs.customconfigs.loginredirection
import com.example.uwlapp.customconfigs.customconfigs.mediumlargeShow
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.models.ProductResponse
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.Commonicator
import com.example.uwlapp.customconfigs.CommonicatorTwo
import com.example.uwlapp.fragments.DialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchAdapter (private val productList: ArrayList<ProductResponse>):

    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.ivSmallImage)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val ivFavorite : ImageView = itemView.findViewById(R.id.ivFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_list_items,
            parent, false)
        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sharedPref = holder.itemView.context.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        var userid = ""
        val currentItem = productList[position]
        mediumlargeShow(currentItem.imagetwo, holder.productImage)
        holder.tvName.text = currentItem.prodname
        holder.tvPrice.text = "£"+currentItem.price
        currentItem.isFavorite = currentItem.isFavorite
        val tokentouse = sharedPref.getString("USERTOKEN", "null")
        val tokendetails = sharedPref.getString("USERTOKEN", null)
            ?.let { customconfigs.tokenconvert(it) }
        if (tokendetails != null) {
            userid = tokendetails.substring(8, 44)
        } else {
            loginredirection(holder.itemView.context)
        }
        if (currentItem.isFavorite) {
            holder.ivFavorite.setImageResource(R.drawable.favouritetrue)
        } else {
            holder.ivFavorite.setImageResource(R.drawable.favourites)
        }
        holder.ivFavorite.setOnClickListener {
            currentItem.isFavorite = !currentItem.isFavorite
            if (currentItem.isFavorite) {
                holder.ivFavorite.setImageResource(R.drawable.favouritetrue)
                CoroutineScope(Dispatchers.IO).launch {
                    val response = rtfinstance().addFavourite(tokentouse.toString(), userid.toString(), currentItem.productid.toString())
                    if (response.isSuccessful == true) {
                        withContext(Dispatchers.Main) {
                            val datatransf = holder.itemView.context as Commonicator
                            //calling the function from the commonicator interface, to display the message
                            //on the dialog
                            datatransf.passDataComFive("Item added to favourites")
//    ***************************************************************************************/
//    *    Title: Send data from one Fragment to another using Kotlin?
//    *    Author: Azhar from Tutorialspoint
//    *    Date: 20 April 2020
//    *    Availability: https://www.tutorialspoint.com/send-data-from-one-fragment-to-another-using-kotlin
//    ***************************************************************************************/

                            DialogFragment().showsDialog
                            //    ***************************************************************************************/
//    *    Title: How to use DialogFragment in Android
//    *    Author: Hüseyin Özkoç
//    *    Date: 16 September 2021
//    *    Availability: https://medium.com/@huseyinozkoc/how-to-use-dialogfragment-in-android-f8095dd0993f
//    *
//    ***************************************************************************************/

                        }

                    }
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/


                }
            } else {
                holder.ivFavorite.setImageResource(R.drawable.favourites)
                CoroutineScope(Dispatchers.IO).launch {
                    val response = rtfinstance().delFavourite(tokentouse.toString(), userid.toString(), currentItem.productid.toString())
                    if (response.isSuccessful == true) {
                        withContext(Dispatchers.Main) {
                            val datatransf = holder.itemView.context as Commonicator
                            //calling the function from the commonicator interface, to display the message
                            //on the dialog
                            datatransf.passDataComFive("Item deleted from favourites")
//    ***************************************************************************************/
//    *    Title: Send data from one Fragment to another using Kotlin?
//    *    Author: Azhar from Tutorialspoint
//    *    Date: 20 April 2020
//    *    Availability: https://www.tutorialspoint.com/send-data-from-one-fragment-to-another-using-kotlin
//    ***************************************************************************************/
                            DialogFragment().showsDialog
//    ***************************************************************************************/
//    *    Title: How to use DialogFragment in Android
//    *    Author: Hüseyin Özkoç
//    *    Date: 16 September 2021
//    *    Availability: https://medium.com/@huseyinozkoc/how-to-use-dialogfragment-in-android-f8095dd0993f
//    *
//    ***************************************************************************************/

                        }}

//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

                }
            }}



        holder.itemView.setOnClickListener {
            val datatransf = holder.itemView.context as Commonicator
            datatransf.passDataComTwo(currentItem.productid)
    }
    }
    override fun getItemCount(): Int {
        return productList.size
    }
}

//    ***************************************************************************************/
//    *    Title: Android: RecyclerView Adapters with Kotlin
//    *    Author: Moksh Jain
//    *    Date: 13 July 2017
//    *    Availability: https://medium.com/@jainmoksh/android-recyclerview-adapters-with-kotlin-ab3e9c8af4fa
//    ***************************************************************************************/

