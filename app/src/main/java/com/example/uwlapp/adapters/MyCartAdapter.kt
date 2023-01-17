package com.example.uwlapp.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.customconfigs.customconfigs.supersmallShow
import com.example.uwlapp.R
import com.example.uwlapp.models.CartResponse
import com.example.uwlapp.models.changedListener
import com.google.gson.Gson


class MyCartAdapter (private val CartResponse: ArrayList<CartResponse>):

    RecyclerView.Adapter<MyCartAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.smallImage)
        val textView1: TextView = itemView.findViewById(R.id.textView1)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
        val textView3: TextView = itemView.findViewById(R.id.textView3)
        val textView4: TextView = itemView.findViewById(R.id.textView4)
        val imageMinus: ImageView = itemView.findViewById(R.id.imageMinus)
        val imageBin: ImageView = itemView.findViewById(R.id.imageView1)
        val imagePlus: ImageView = itemView.findViewById(R.id.imagePlus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_cart_items,
            parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = CartResponse[position]

        supersmallShow(currentItem.imagetwo, holder.productImage)
        holder.textView1.text = currentItem.productshortname
        holder.textView2.text = currentItem.productname
        holder.textView3.text = "£${currentItem.price}"
        holder.textView4.text = currentItem.quantity.toString()
        holder.imageMinus.changedListener {
            currentItem.quantity = currentItem.quantity - 1
            holder.textView4.text = currentItem.quantity.toString()
            val sharedPreferences: SharedPreferences = holder.itemView.context.getSharedPreferences("CART", Context.MODE_PRIVATE)
            var totalPrice = sharedPreferences.getFloat("TOTAL", 0.0f)
            var valuesFromHash = sharedPreferences.getString("PRODUCTLIST", null)
            var totalQTY = sharedPreferences.getInt("TOTALQTY", 0)
            totalQTY -= 1
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val gson = Gson()
            var getHashMap = gson.fromJson(valuesFromHash, HashMap::class.java) as HashMap<String, Int>

//    ***************************************************************************************/
//    *    Title: Gson
//    *    Author: Google/Gson
//    *    Date: 25 October 2022
//    *    Code version: 2.1.0
//    *    Availability: https://github.com/google/gson
//    ***************************************************************************************/



            totalPrice -= currentItem.price.toFloat()
            getHashMap[currentItem.productid] = currentItem.quantity
            if ((getHashMap[currentItem.productid]).toString().toInt() == 0) {
                CartResponse.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, CartResponse.size)
                getHashMap.remove(currentItem.productid)
                holder.imageMinus.isEnabled = false
            }
            if (currentItem.quantity > 0) {
                holder.imageMinus.isEnabled = true
            }

            val json: String = gson.toJson(getHashMap)
            editor.putString("PRODUCTLIST", json).apply()
            editor.putFloat("TOTAL", totalPrice).apply()
            editor.putInt("TOTALQTY", totalQTY).apply()
            Log.e("TAG", "onBindViewHolder: $totalQTY")
            var totalPriceTwo = sharedPreferences.getFloat("TOTAL", 0.0f)

        }

        holder.imageBin.changedListener {
            val sharedPreferences: SharedPreferences = holder.itemView.context.getSharedPreferences("CART", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            var valuesFromHash = sharedPreferences.getString("PRODUCTLIST", null)
            var totalPrice = sharedPreferences.getFloat("TOTAL", 0.0f)
            var totalQTY = sharedPreferences.getInt("TOTALQTY", 0)
            val gson = Gson()
//==================================================================================================
            var getHashMap = gson.fromJson(valuesFromHash, HashMap::class.java) as HashMap<String, Int>
            totalPrice = totalPrice - (currentItem.price.toFloat() * currentItem.quantity)
            totalQTY -= currentItem.quantity
            getHashMap[currentItem.productid] = currentItem.quantity
            getHashMap.remove(currentItem.productid)
            val json: String = gson.toJson(getHashMap)
            editor.putString("PRODUCTLIST", json).apply()
            editor.putFloat("TOTAL", totalPrice).apply()
            editor.putInt("TOTALQTY", totalQTY).apply()

//==================================================================================================

            CartResponse.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, CartResponse.size)

        }

        holder.imagePlus.changedListener {
            Log.e("TAG", "TOTAL PRICE ==>>>>>>>>>>>>>>>>>>>>>: $currentItem.quantity")
            if (currentItem.quantity < currentItem.availableqty) {
                currentItem.quantity = currentItem.quantity + 1
                holder.textView4.text = currentItem.quantity.toString()
                holder.imagePlus.isEnabled = true

                val sharedPreferences: SharedPreferences =
                    holder.itemView.context.getSharedPreferences("CART", Context.MODE_PRIVATE)
                var totalPrice = sharedPreferences.getFloat("TOTAL", 0.0f)
                var totalQTY = sharedPreferences.getInt("TOTALQTY", 0)
                totalQTY += 1
                Log.e("TAG", "onBindViewHolder: $totalQTY")


                totalPrice += currentItem.price.toFloat()
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                var valuesFromHash = sharedPreferences.getString("PRODUCTLIST", null)
                val gson = Gson()
                var getHashMap =
                    gson.fromJson(valuesFromHash, HashMap::class.java) as HashMap<String, Int>
                getHashMap[currentItem.productid] = currentItem.quantity.toFloat().toInt()
                val json: String = gson.toJson(getHashMap)

//    ***************************************************************************************/
//    *    Title: Gson
//    *    Author: Google/Gson
//    *    Date: 25 October 2022
//    *    Code version: 2.1.0
//    *    Availability: https://github.com/google/gson
//    ***************************************************************************************/

                editor.putString("PRODUCTLIST", json).apply()
                editor.putFloat("TOTAL", totalPrice).apply()
                editor.putInt("TOTALQTY", totalQTY).apply()

            }}}


    override fun getItemCount(): Int {
        return CartResponse.size
    }
}

//    ***************************************************************************************/
//    *    Title: Android: RecyclerView Adapters with Kotlin
//    *    Author: Moksh Jain
//    *    Date: 13 July 2017
//    *    Availability: https://medium.com/@jainmoksh/android-recyclerview-adapters-with-kotlin-ab3e9c8af4fa
//    ***************************************************************************************/


