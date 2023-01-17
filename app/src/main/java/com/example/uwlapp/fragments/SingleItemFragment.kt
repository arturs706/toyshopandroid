package com.example.uwlapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.uwlapp.customconfigs.customconfigs.largeShow
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.customconfigs.customconfigs.smallShow
import com.example.uwlapp.customconfigs.Commonicator
import com.example.uwlapp.databinding.FragmentSingleItemBinding
import com.google.gson.Gson


class SingleItemFragment : Fragment() {
    private lateinit var binding: FragmentSingleItemBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productList()
    }
    @SuppressLint("SetTextI18n")
    private fun productList() {
        val sharedPreferences = requireActivity().getSharedPreferences("CART", Context.MODE_PRIVATE)
        lifecycleScope.launchWhenCreated {
            try {
                var getHashMap : HashMap<String, Int> = HashMap()
                val productId = arguments?.getString("dataproduct")
                var totalPrice = sharedPreferences.getFloat("TOTAL", 0.0f)
                val valuesFromHash = sharedPreferences.getString("PRODUCTLIST", null)
                var totalQTY = sharedPreferences.getInt("TOTALQTY", 0)
                val gson = Gson()
                if (valuesFromHash != null) {
                    getHashMap = gson.fromJson(valuesFromHash, HashMap::class.java) as HashMap<String, Int>
                }
                var checkVal = 0

                val response = rtfinstance().getSingleProduct(productId.toString())
                if (response.isSuccessful) {
                    val productName = response.body()?.product?.get(0)?.prodname.toString()
                    var productQty = response.body()?.product?.get(0)?.availableqty.toString().toInt()
                    if (getHashMap != null) {
                        for (i in getHashMap) {
                            if (i.key == productId) {
                                checkVal = getHashMap.get(productId)!!.toString().toFloat().toInt()
                            }}}
                    productQty -= checkVal
                    val imageR = binding.iv1
                    val imageRTwo = binding.iv2
                    val imageRThree = binding.iv3
                    binding.tv1.text = productName.uppercase()
                    largeShow(response.body()?.product?.get(0)?.imageone.toString(), imageR)
                    smallShow(response.body()?.product?.get(0)?.imagetwo.toString(), imageRTwo)
                    smallShow(response.body()?.product?.get(0)?.imagethree.toString(), imageRThree)

                    binding.tv2.text = response.body()?.product?.get(0)?.proddescr.toString()
                    binding.tv3.text = "£${response.body()?.product?.get(0)?.price.toString()}"
                    binding.addtocart.setOnClickListener{
                        BottomsheetFragment().showsDialog
                        totalQTY += 1
                        val editor = sharedPreferences.edit()
                        if (getHashMap != null) {
                            for (i in getHashMap) {
                                if (i.key == productId) {
                                    checkVal = getHashMap.get(productId)!!.toString().toFloat().toInt()
                                }}}
                        //passing data to bottomsheet via bundle
                        val datatransf = activity as Commonicator
                        val arrayList = ArrayList<String>()

                        arrayList.add((checkVal + 1).toString())
                        arrayList.add(response.body()?.product?.get(0)?.prodname.toString())
                        arrayList.add(response.body()?.product?.get(0)?.imageone.toString())
                        arrayList.add(("%.2f".format(response.body()?.product?.get(0)?.price.toString().toFloat())))
                        arrayList.add(("£%.2f".format(totalPrice + response.body()?.product?.get(0)?.price.toString().toFloat())))
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


                        if (checkVal + 1 < productQty) {
                            checkVal += 1
                            totalPrice += response.body()?.product?.get(0)?.price.toString().toFloat()
                            getHashMap[productId.toString()] = checkVal
                            editor.putString("PRODUCTLIST", gson.toJson(getHashMap))
                            editor.putFloat("TOTAL", totalPrice)
                            editor.putInt("TOTALQTY", totalQTY)
                            editor.apply()
                        }
                    }
                } else {
                    binding.tv1.text = response.errorBody()?.string().toString()
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.e("Error", it) }
            }}
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

    }
}