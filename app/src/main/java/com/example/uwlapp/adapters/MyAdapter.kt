package com.example.uwlapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.customconfigs.customconfigs.mediumlargeShow
import com.example.uwlapp.customconfigs.customconfigs.supersmallShow
import com.example.uwlapp.models.ProductResponse
import com.example.uwlapp.models.changedListener
import com.example.uwlapp.R
import com.example.uwlapp.customconfigs.Commonicator
import com.example.uwlapp.fragments.BottomsheetFragment
import com.google.gson.Gson


class MyAdapter (private val productList: ArrayList<ProductResponse>):

    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.imageView1)
        val productImage2: ImageView = itemView.findViewById(R.id.imageView2)
        val productImage3: ImageView = itemView.findViewById(R.id.imageView3)
        val productImage4: ImageView = itemView.findViewById(R.id.imageView4)
        val productName: TextView = itemView.findViewById(R.id.textView1)
        val productPrice: TextView = itemView.findViewById(R.id.textView2)
        val productDesc: TextView = itemView.findViewById(R.id.textView3)
        val addToCart: Button = itemView.findViewById(R.id.button1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //References shared preferences with the name cart

        val sharedPref = holder.itemView.context.getSharedPreferences("CART", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val currentItem = productList[position]
        mediumlargeShow(currentItem.imageone, holder.productImage)
        supersmallShow(currentItem.imagetwo, holder.productImage2)
        supersmallShow(currentItem.imagethree, holder.productImage3)
        supersmallShow(currentItem.imagefour, holder.productImage4)
        holder.productName.text = currentItem.prodname
        holder.productPrice.text = "£"+currentItem.price
        holder.productDesc.text = currentItem.proddescr
        val gson = Gson();
        var checkVal = 0


        //getting the string from key cart
        val getHashMapString = sharedPref.getString("PRODUCTLIST", null)

        val getHashMap = gson.fromJson(getHashMapString, HashMap::class.java)
        if (getHashMap != null) {
            for (i in getHashMap) {
                if (i.key == currentItem.productid) {
                    checkVal = getHashMap.get(currentItem.productid)!!.toString().toFloat().toInt()
                }}}
//    ***************************************************************************************/
//    *    Title: Gson
//    *    Author: Google/Gson
//    *    Date: 25 October 2022
//    *    Code version: 2.1.0
//    *    Availability: https://github.com/google/gson
//    ***************************************************************************************/

        currentItem.availableqty = currentItem.availableqty - checkVal


        var quantity = 0
        if (currentItem.availableqty < 1) {
            holder.addToCart.isEnabled = false
            holder.addToCart.text = "Out of Stock"
        }

        holder.addToCart.changedListener {


            var getHashMp = sharedPref.getString("PRODUCTLIST", null)
            var totalPrice = sharedPref.getFloat("TOTAL", 0.0f)
            var totalQTY = sharedPref.getInt("TOTALQTY", 0)
            val booleancheck = getHashMp == null
            var hmapvalues = HashMap<String, Int>()
            var arrayList = ArrayList<String>()
            if(booleancheck) {
                hmapvalues.put(currentItem.productid, 1)
                totalQTY = 1
                totalPrice += currentItem.price.toFloat()
                editor.putString("PRODUCTLIST", gson.toJson(hmapvalues))
                editor.putFloat("TOTAL", totalPrice)
                editor.putInt("TOTALQTY", totalQTY)
                editor.apply()
            } else {
                val responseHM = gson.fromJson(getHashMp, HashMap::class.java) as HashMap<String, Int>
//    ***************************************************************************************/
//    *    Title: Gson
//    *    Author: Google/Gson
//    *    Date: 25 October 2022
//    *    Code version: 2.1.0
//    *    Availability: https://github.com/google/gson
//    ***************************************************************************************/

                if (responseHM.containsKey(currentItem.productid)) {
                    quantity = responseHM.get(currentItem.productid)!!
                    quantity += 1
                    totalQTY += 1
                    responseHM.put(currentItem.productid, quantity)
                    totalPrice += currentItem.price.toFloat()
                    editor.putInt("TOTALQTY", totalQTY)
                    editor.putString("PRODUCTLIST", gson.toJson(responseHM))
                    editor.putFloat("TOTAL", totalPrice)
                    editor.apply()

                } else {
                    responseHM.put(currentItem.productid, 1)
                    totalPrice += currentItem.price.toFloat()
                    totalQTY += 1
                    editor.putString("PRODUCTLIST", gson.toJson(responseHM))
                    editor.putFloat("TOTAL", totalPrice)
                    editor.putInt("TOTALQTY", totalQTY)
                    editor.apply()
                }
            }
            val datatransf = holder.itemView.context as Commonicator
            if (quantity == 0) {
                quantity = 1
            }
            arrayList.add(quantity.toString())
            arrayList.add(currentItem.prodname)
            arrayList.add(currentItem.imageone)
            arrayList.add(currentItem.price)
            arrayList.add(("£%.2f".format(totalPrice)).toString())
            datatransf.passDataComThree(arrayList)


//    ***************************************************************************************/
//    *    Title: Send data from one Fragment to another using Kotlin?
//    *    Author: Azhar from Tutorialspoint
//    *    Date: 20 April 2020
//    *    Availability: https://www.tutorialspoint.com/send-data-from-one-fragment-to-another-using-kotlin
//    ***************************************************************************************/

            BottomsheetFragment().showsDialog
//    ***************************************************************************************/
//    *    Title: BottomSheetDialogFragment Made Simpler
//    *    Author: Guneet Singh
//    *    Date: 24 April 2020
//    *    Availability: https://medium.com/swlh/bottomsheetdialogfragment-made-simpler-b32fa8e20928
//    ***************************************************************************************/

            Log.e("TAG", "Array: $arrayList" )
            currentItem.availableqty = currentItem.availableqty - 1
            if (currentItem.availableqty < 1) {
                holder.addToCart.isEnabled = false
                holder.addToCart.text = "Out of Stock"
            }
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
