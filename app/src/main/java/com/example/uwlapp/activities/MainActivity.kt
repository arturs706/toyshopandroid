package com.example.uwlapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.uwlapp.customconfigs.customconfigs.cart_redirecton
import com.example.uwlapp.customconfigs.customconfigs.lightscreen
import com.example.uwlapp.customconfigs.Commonicator
import com.example.uwlapp.databinding.ActivityMainBinding
import com.example.uwlapp.fragments.*
import com.stripe.android.PaymentConfiguration
import java.util.*

class MainActivity : AppCompatActivity(), Commonicator {
    //assigning the binding variable, shared preferences and binding
    private val HomeFragment = HomeFragment()
    private val SearchFragment = SearchFragment()
    private val FavouritesFragment = FavouritesFragment()
    private val AccountFragment = AccountFragment()
    private val ProductFragment = ProductFragment()
    private val OrderFragment = OrderFragment()
    private val SingleOrderFragment = SingleOrderFragment()
    private val SingleItemFragment = SingleItemFragment()
    private val ContactusFragment = ContactusFragment()
    private lateinit var binding: ActivityMainBinding
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("CART", Context.MODE_PRIVATE)
    }
    //listen for changes in the shared preferences for total items in cart
    private val listenChanges = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
      if (key == "TOTALQTY") {
            val totalQTY = sharedPreferences.getInt("TOTALQTY", 0)
            binding.tvCart.text = totalQTY.toString()

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val totalQTY = sharedPreferences.getInt("TOTALQTY", 0)
        val value = intent.getStringExtra("FRAGMENT")
        setContentView(binding.root)
//    ***************************************************************************************/
//    *    Title: View Binding
//    *    Author: Android Developers
//    *    Date: 24 March 2022
//    *    Availability: https://developer.android.com/topic/libraries/data-binding/expressions
//    ***************************************************************************************/

        //remove the action bar and navigation bar from the splash screen
        lightscreen(this)
        binding.tvCart.text = totalQTY.toString()
        //assign listener to shared preferences
        sharedPreferences.registerOnSharedPreferenceChangeListener(listenChanges)
//    ***************************************************************************************/
//    *    Title: Using Shared Preference Change Listener
//    *    Author: Anchit
//    *    Date: 25 October 2021
//    *    Availability: https://mobikul.com/using-shared-preference-change-listener/
//    ***************************************************************************************/

        Log.e("FRAGMENT", totalQTY.toString())
        // preload Stripe payment configuration
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51Jz1TBDRFTiRmgTHih3IZZmqV8ZJDFKwL1i7E5YELbbMY0wh2cHKa8dHq9eggetk4oZYVKbK9yQhGseTpXv0HgA200xgu8otjZ"
        )
//    ***************************************************************************************/
//    *    Title: Stripe Android SDK
//    *    Author: Stripe
//    *    Availability: https://stripe.com/docs/libraries/android
//    ***************************************************************************************/

        //redirect to cart fragment if the user clicks on the cart icon
        cart_redirecton(binding.ivCart, this)
        //set the home fragment as the default fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentContainer, HomeFragment())
            commit()
            binding.ivHome.setImageResource(R.drawable.homeactive)
            binding.ivOrders.setImageResource(R.drawable.explore)
        }

        //pass the reference from other activities to this activity, to be used for changing fragments
        when(value){
            "account" -> redirectSwitcher(AccountFragment)
            "search" -> redirectSwitcher(SearchFragment)
            "favourites" -> redirectSwitcher(FavouritesFragment)
            "orders" -> redirectSwitcher(OrderFragment)
            "contactus" -> redirectSwitcher(ContactusFragment)
        }
        //function to change the fragment when the user clicks on the specific icon
        fragmentSwitcher(R.id.flFragmentContainer, binding.ivHome, HomeFragment)
        fragmentSwitcher(R.id.flFragmentContainer, binding.ivSearch, SearchFragment)
        fragmentSwitcher(R.id.flFragmentContainer, binding.ivFavourites, FavouritesFragment)
        fragmentSwitcher(R.id.flFragmentContainer, binding.ivSearch1, SearchFragment)
        fragmentSwitcher(R.id.flFragmentContainer, binding.ivAccountTop, AccountFragment)
        fragmentSwitcher(R.id.flFragmentContainer, binding.ivContactUs, ContactusFragment)
        fragmentSwitcher(R.id.flFragmentContainer, binding.ivAccount, AccountFragment)



        //listens changes of the fragment container and changes the icon to active, and the rest to inactive
        supportFragmentManager.addFragmentOnAttachListener { fragmentManager, fragment ->
            if (fragmentManager.fragments[0] is HomeFragment) {
                fragmentSwitcher(R.id.flFragmentContainer, binding.ivOrders, ProductFragment)
            } else {
                fragmentSwitcher(R.id.flFragmentContainer, binding.ivOrders, OrderFragment)
            }}}
//    ***************************************************************************************/
//    *    Title: FragmentOnAttachListener
//    *    Author: Android Developers
//    *    Date: 22 December 2022
//    *    Availability: https://developer.android.com/reference/androidx/fragment/app/FragmentOnAttachListener
//    ***************************************************************************************/

    //function to change the fragment when the user clicks on the specific icon
    private fun redirectSwitcher(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragmentContainer, fragment)
            commit()
        }
    }

    //function to change the fragment when the user clicks on the specific icon along with changing the icon to active
    private fun fragmentSwitcher(int: Int, ImageView: ImageView, fragment: Fragment) {
        ImageView.setOnClickListener() {
            supportFragmentManager.beginTransaction().apply {
                replace(int, fragment)
                commit()
            }

            supportFragmentManager.addFragmentOnAttachListener { fragmentManager, fragment ->
                if (fragmentManager.fragments[0] is HomeFragment) {
                    binding.ivHome.setImageResource(R.drawable.homeactive)
                    binding.ivOrders.setImageResource(R.drawable.explore)

                }
                else {
                    binding.ivHome.setImageResource(R.drawable.home)
                }
                if (fragmentManager.fragments[0] is SearchFragment) {
                    binding.ivSearch.setImageResource(R.drawable.searchactive)
                    binding.ivOrders.setImageResource(R.drawable.orders)

                }
                else {
                    binding.ivSearch.setImageResource(R.drawable.search)
                }
                if (fragmentManager.fragments[0] is FavouritesFragment) {
                    binding.ivFavourites.setImageResource(R.drawable.favouritesactive)
                    binding.ivOrders.setImageResource(R.drawable.orders)

                }
                else {
                    binding.ivFavourites.setImageResource(R.drawable.favourites)
                }
                if (fragmentManager.fragments[0] is AccountFragment) {
                    binding.ivAccount.setImageResource(R.drawable.accountactive)
                    binding.ivOrders.setImageResource(R.drawable.orders)

                }
                else {
                    binding.ivAccount.setImageResource(R.drawable.account)
                }


            }}}
//    ***************************************************************************************/
//    *    Title: Fragment transactions
//    *    Author: Android Developers
//    *    Date: 27 October 2022
//    *    Availability: https://developer.android.com/guide/fragments/transactions
//    ***************************************************************************************/

    //function for enclosing the data from one fragment to another, using the bundle
    override fun passDataCom(editTextData: String) {
        val bundle = Bundle()
        bundle.putString("data", editTextData)
        val transferData = this.supportFragmentManager.beginTransaction()
        SingleOrderFragment.arguments = bundle
        transferData.replace(R.id.flFragmentContainer, SingleOrderFragment).commit()
    }
    //function for enclosing the data from one fragment to another, using the bundle used for different fragment

    override fun passDataComTwo(editTextData: String) {
        val bundle = Bundle()
        bundle.putString("dataproduct", editTextData)
        val transferData = this.supportFragmentManager.beginTransaction()
        SingleItemFragment.arguments = bundle
        transferData.replace(R.id.flFragmentContainer, SingleItemFragment).commit()
    }
    //function for enclosing the data from one fragment to another, using the bundle used for different fragment

    override fun passDataComThree(editTextData: ArrayList<String>) {
        val bundle = Bundle()
        val frag2 = BottomsheetFragment()
        frag2.arguments = bundle
        bundle.putStringArrayList("itemdata", editTextData)
        frag2.show(supportFragmentManager, "TAG")
    }
    //function for enclosing the data from one fragment to another, using the bundle used for different fragment

    override fun passDataComFour(editTextData: ArrayList<String>) {
        val bundle = Bundle()
        val frag2 = BottomsheetFragment()
        frag2.arguments = bundle
        bundle.putStringArrayList("singleitemdata", editTextData)
        frag2.show(supportFragmentManager, "TAG")
    }
    override fun passDataComFive(editTextData: String) {
        val bundle = Bundle()
        val frag2 = DialogFragment()
        frag2.arguments = bundle
        bundle.putString("message", editTextData)
        frag2.show(supportFragmentManager, "TAG")
    }
}
//    ***************************************************************************************/
//    *    Title: Send data from one Fragment to another using Kotlin?
//    *    Author: Azhar from Tutorialspoint
//    *    Date: 20 April 2020
//    *    Availability: https://www.tutorialspoint.com/send-data-from-one-fragment-to-another-using-kotlin
//    ***************************************************************************************/
