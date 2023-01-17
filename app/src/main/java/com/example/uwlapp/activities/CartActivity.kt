package com.example.uwlapp.activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwlapp.customconfigs.customconfigs
import com.example.uwlapp.customconfigs.customconfigs.fscreen_redirecton
import com.example.uwlapp.customconfigs.customconfigs.lightscreen
import com.example.uwlapp.customconfigs.customconfigs.redirecting
import com.example.uwlapp.customconfigs.customconfigs.rtfinstance
import com.example.uwlapp.adapters.MyCartAdapter
import com.example.uwlapp.models.CartResponse
import com.example.uwlapp.MainActivity
import com.example.uwlapp.customconfigs.customconfigs.tokenconvert
import com.example.uwlapp.databinding.ActivityCartBinding
import com.google.gson.Gson
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject



class CartActivity : AppCompatActivity() {


    //assigning the binding variable, Stripe, Payment button and the shared preferences
    private lateinit var stripe: Stripe
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var payButton: Button

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("CART", Context.MODE_PRIVATE)
    }
    val userSharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
    }

    //listens to changes in Total Price, so value could be updated accordingly.
    //Also if price is less then 1, the particular textview is hidden and the button is hidden
    private val listenChanges = OnSharedPreferenceChangeListener { prefs, key ->
        if (key == "TOTAL") {
            val totalPrice = sharedPreferences.getFloat("TOTAL", 0.0f)
            binding.totalPrice.text = "£%.2f".format(totalPrice)
            if (totalPrice < 0.01) {
                binding.totalpriceTxt.visibility = View.GONE
                binding.totalPrice.visibility = View.GONE
                binding.continueToProduct.visibility = View.GONE
            }
            if (totalPrice < 0.01) {
                binding.cartisempty.visibility = View.VISIBLE
            }
        } else if (key == "TOTALQTY") {
            val totalQTY = sharedPreferences.getInt("TOTALQTY", 0)
            binding.tvCart.text = totalQTY.toString()
        }
    }

//    ***************************************************************************************/
//    *    Title: Using Shared Preference Change Listener
//    *    Author: Anchit
//    *    Date: 25 October 2021
//    *    Availability: https://mobikul.com/using-shared-preference-change-listener/
//    *
//    ***************************************************************************************/

    private lateinit var binding: ActivityCartBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyCartAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)

        //creating an array list of the cart items
        val arraylist = arrayListOf<CartResponse>()
        setContentView(binding.root)

//    ***************************************************************************************/
//    *    Title: View Binding
//    *    Author: Android Developers
//    *    Date: 24 March 2022
//    *    Availability: https://developer.android.com/topic/libraries/data-binding/expressions
//    ***************************************************************************************/

        //applying function that removes the status bar and makes the screen full screen
        lightscreen(this@CartActivity)
        //redirecting to the main activity
        fscreen_redirecton(binding.backarrow, this)
        //navigate to product fragment from this activity with the help of intent
        binding.ivHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //getting total qty for the number next to the cart icon
        val totalQTY = sharedPreferences.getInt("TOTALQTY", 0)
        binding.tvCart.text = totalQTY.toString()

        //redirecting to the main activity, passing the ref for the fragment to be opened, when users clicks particular icons
        redirecting("account", binding.ivAccount, this)
        redirecting("account", binding.ivAccountTop, this)
        redirecting("search", binding.ivSearch, this)
        redirecting("favourites", binding.ivFavourites, this)
        redirecting("orders", binding.ivOrders, this)
        redirecting("contactus", binding.ivContactUs, this)

        //stripe ============================================
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        payButton = binding.continueToProduct
        payButton.setOnClickListener(::onPayClicked)
        stripe = Stripe(this, PaymentConfiguration.getInstance(applicationContext).publishableKey)

//    ***************************************************************************************/
//    *    Title: Stripe Android SDK
//    *    Author: Stripe
//    *    Availability: https://stripe.com/docs/libraries/android
//    ***************************************************************************************/

        //initialising the Gson object for parsing the json data, and a new HashMap

        val gson = Gson()
//    ***************************************************************************************/
//    *    Title: Gson
//    *    Author: Google/Gson
//    *    Date: 25 October 2022
//    *    Code version: 2.1.0
//    *    Availability: https://github.com/google/gson
//    ***************************************************************************************/

        var getHashMap: HashMap<*, *>?
        val getHashMapString = sharedPreferences.getString("PRODUCTLIST", null)
        val totalPrice = sharedPreferences.getFloat("TOTAL", 0.0f)
        //listening to the changes in the shared preferences for a value change
        sharedPreferences.registerOnSharedPreferenceChangeListener(listenChanges)
        //assigning values for a HashMap
        if (getHashMapString != null) {
            getHashMap = gson.fromJson(getHashMapString, HashMap::class.java)
        }
        getHashMap = gson.fromJson(getHashMapString, HashMap::class.java)
        if (getHashMap != null) {
            //getting the values from the HashMap
            for (key in getHashMap.keys) {
                val value = getHashMap[key].toString().toFloat().toInt()
                //initialising new CoroutineScope for the network call
                lifecycleScope.launchWhenCreated {
//    ***************************************************************************************/
//    *    Title: Lifecycle-aware coroutine scopes
//    *    Author: Android Developers
//    *    Availability: https://developer.android.com/topic/libraries/architecture/coroutines
//    ***************************************************************************************/
                    try {
                        //removes the Cart is empty text view, so if network call is delayed, the user will not see the text view, but progress bar
                        binding.cartisempty.visibility = View.GONE
                        //making a network call to get the product details
                        val response = rtfinstance().getSingleProduct(key.toString())
                        if (response.isSuccessful) {
                            //if the network call is successful, then the progress bar will be gone
                            binding.progressBar.visibility = View.GONE
                            //if total price is less than 0.01, then the total price text view and the continue to product button will be gone
                            if (totalPrice > 0.01) {
                                binding.totalPrice.visibility = View.VISIBLE
                                binding.totalpriceTxt.visibility = View.VISIBLE
                            }
                            //total price will be displayed
                            binding.totalPrice.text = "£%.2f".format(totalPrice)
                            if (totalPrice > 0.01) {
                                binding.cartisempty.visibility = View.GONE
                                binding.continueToProduct.visibility = View.VISIBLE
                            }
                            //getting the response body values
                            val productName = response.body()?.product?.get(0)?.prodname.toString()
                            val productName2 = productName.substring(0, productName.indexOf(" ", productName.indexOf(" ") + 1))
                            val productImage = response.body()?.product?.get(0)?.imagetwo.toString()
                            val productPrice = response.body()?.product?.get(0)?.price.toString()
                            val availableQty = response.body()?.product?.get(0)?.availableqty.toString()
                            //adding the values to the array list for the recycler view
                            if (getHashMap.contains(key) == false) {
                                arraylist.add(CartResponse(productName2, productName, value, productImage, productPrice, key.toString(), availableQty.toFloat().toInt()))
                            } else {
                                arraylist.add(CartResponse(productName2, productName, value, productImage, productPrice, key.toString(), availableQty.toFloat().toInt()))
                            }
                           //initialising the recycler view and the adapter with the array list values
                            recyclerView = binding.recyclerViewCart
                            recyclerView.layoutManager = LinearLayoutManager(this@CartActivity)
                            recyclerView.setHasFixedSize(true)
                            adapter = MyCartAdapter(arraylist)
                            recyclerView.adapter = adapter
                        } else {
                            var toastError = response.errorBody()?.string().toString()
                        }
                    } catch (e: Exception) {
                        e.localizedMessage?.let { Log.e("Error", it) }
                    }}}}}

//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/


    //function for the payment sheet, passing the payment details through the HTTP request body, incuding total price gathered from the shared preferences
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onPayClicked(view: View) {
//    ***************************************************************************************/
//    *    Title: Stripe Android SDK
//    *    Author: Stripe
//    *    Availability: https://stripe.com/docs/libraries/android
//    ***************************************************************************************/

    var fullname: String = ""
    var email: String = ""
//getting a name from the saved token
    val token = userSharedPreferences.getString("USERTOKEN", null)
    if (token != null) {
        val tokenconv = tokenconvert(token)
        val userid = tokenconv.substring(8, 44)
        CoroutineScope(Dispatchers.IO).launch {
                val response = rtfinstance().getSingleUser(token.toString(), userid)
                if (response.isSuccessful) {
                    fullname = response.body()?.users?.get(0)?.fullname.toString()
                    email = response.body()?.users?.get(0)?.email.toString()
                    val totalPrice = sharedPreferences.getFloat("TOTAL", 0.0f)
                    val jsonObject = JSONObject()
                        .accumulate("customerid", userid)
                        .accumulate("customername", fullname)
                        .accumulate("customeremail", email)
                        .accumulate("paymentamount", (totalPrice * 100).toInt())
//creating a new HTTP request body
                    val requestBody = jsonObject.toString().toRequestBody(
                        "application/json".toMediaTypeOrNull(),
                    )
//    ***************************************************************************************/
//    *    Title: Simple POST request on Android Kotlin using Retrofit
//    *    Author: Sazzad Hissain Khan
//    *    Date: 01 April 2020
//    *    Availability: https://medium.com/swlh/simplest-post-request-on-android-kotlin-using-retrofit-e0a9db81f11a
//    ***************************************************************************************/

                    //initialising the CoroutineScope for the network call to get the payment intent, configuring the payment sheet and confirming the identity of Stripe client
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = rtfinstance().stripeCallback(token.toString(), requestBody)
                            if (response.isSuccessful) {
                                val configuration = PaymentSheet.Configuration("ToyStore")
                                val paymentIntentClientSecret = response.body()?.clientSecret.toString()
                                paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration)
                            } else {
                                withContext(Dispatchers.Main) {
                                    var toastError = response.errorBody()?.string().toString()
                                }}
                        } catch (e: Exception) {
                            e.localizedMessage?.let { Log.e("Error", it) }
                        }}
                } else {
                    withContext(Dispatchers.Main) {
                        var toastError = response.errorBody()?.string().toString()
}}}}}
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

    //function for the payment sheet result
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {

//    ***************************************************************************************/
//    *    Title: Stripe Android SDK
//    *    Author: Stripe
//    *    Availability: https://stripe.com/docs/libraries/android
//    ***************************************************************************************/

                //if payment is confirmed Toast message will be displayed
                //if payment is confirmed, the shared preferences will be cleared
                Toast.makeText(this, "Payment completed", Toast.LENGTH_SHORT).show()
                val sharedPreferences: SharedPreferences = getSharedPreferences("CART", Context.MODE_PRIVATE)
                val orderItems = sharedPreferences.getString("PRODUCTLIST", null)
                val totalPrice = sharedPreferences.getFloat("TOTAL", 0.0f)
                val hashmaped = Gson().fromJson(orderItems, HashMap::class.java)
//    ***************************************************************************************/
//    *    Title: Gson
//    *    Author: Google/Gson
//    *    Date: 25 October 2022
//    *    Code version: 2.1.0
//    *    Availability: https://github.com/google/gson
//    ***************************************************************************************/


                val token = userSharedPreferences.getString("USERTOKEN", null)
                //function that will send the order details to the database
                getvaluefromdb(this@CartActivity,totalPrice,token,hashmaped)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.remove("PRODUCTLIST").apply()
                editor.remove("TOTAL").apply()
                editor.remove("TOTALQTY").apply()
                //redirecting the user to the main activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            //if cancelled, Toast message will be displayed
            is PaymentSheetResult.Canceled -> {
                Log.i(ContentValues.TAG, "Payment canceled!")
            }
            //if failed, Toast message will be displayed
            is PaymentSheetResult.Failed -> {
                Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show()
            }}}}
//    ***************************************************************************************/
//    *    Title: Stripe Android SDK
//    *    Author: Stripe
//    *    Availability: https://stripe.com/docs/libraries/android
//    ***************************************************************************************/

//function that will send the order details to the database for the particular user
@RequiresApi(Build.VERSION_CODES.O)
fun getvaluefromdb(context: Context, totalPrice: Float, token: String?, hashmaped: HashMap<*, *>) {
    val tokendetails = token?.let { customconfigs.tokenconvert(it) }
    var orderiteminc = tokendetails?.substring(8, 44)
    val jsonObject = JSONObject()
        .accumulate("userid", orderiteminc)
        .accumulate("total", totalPrice)
    val requestBody = jsonObject.toString().toRequestBody(
        "application/json".toMediaTypeOrNull(),
    )
//    ***************************************************************************************/
//    *    Title: Simple POST request on Android Kotlin using Retrofit
//    *    Author: Sazzad Hissain Khan
//    *    Date: 01 April 2020
//    *    Availability: https://medium.com/swlh/simplest-post-request-on-android-kotlin-using-retrofit-e0a9db81f11a
//    ***************************************************************************************/

    //initialising the CoroutineScope for the network call to send the order details to the database
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = rtfinstance().createorder(token.toString(),requestBody)
            val orderid = response.body()?.response.toString()
            val orderiteminc = orderid.substring(18, orderid.length - 2)
            val parsedorderid: Long = orderiteminc.toLong()

            if (response.isSuccessful) {
                for (key in hashmaped.keys) {
                    val value = hashmaped[key].toString().toFloat().toInt()
                    val jobj = JSONObject()
                        .accumulate("orderidretr", parsedorderid.toString().toLong().toBigInteger())
                        .accumulate("productid", key.toString())
                        .accumulate("quantity", value.toString().toLong().toBigInteger())
                    val rBody = jobj.toString().toRequestBody(
                        "application/json".toMediaTypeOrNull(),
                    )
//    ***************************************************************************************/
//    *    Title: Simple POST request on Android Kotlin using Retrofit
//    *    Author: Sazzad Hissain Khan
//    *    Date: 01 April 2020
//    *    Availability: https://medium.com/swlh/simplest-post-request-on-android-kotlin-using-retrofit-e0a9db81f11a
//    ***************************************************************************************/

                    //initialising the CoroutineScope for the network call to send the order details to the database
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = rtfinstance().orderItems(token.toString(),rBody)
                            if (response.isSuccessful == false) {
                                withContext(Dispatchers.Main) {
                                    val toastError = response.errorBody()?.string().toString()
                                    Toast.makeText(context, toastError, Toast.LENGTH_SHORT).show()
                                }}
                        } catch (e: Exception) {
                            e.localizedMessage?.let { Log.e("Error ->", it) }
                        }}}
            } else {
                withContext(Dispatchers.Main) {
                    var toastError = response.errorBody()?.string().toString()
                    Toast.makeText(context, toastError, Toast.LENGTH_SHORT).show()

                }}
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("Error == ", it) }
        }}
}
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

