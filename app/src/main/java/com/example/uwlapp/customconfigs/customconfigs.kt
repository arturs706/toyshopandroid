package com.example.uwlapp.customconfigs

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.uwlapp.R
import coil.load
import coil.request.CachePolicy
import coil.size.Precision
import coil.size.Scale
import com.example.myapp.api.RtfClient
import com.example.uwlapp.api.ApiInterface
import com.example.uwlapp.MainActivity
import com.example.uwlapp.activities.CartActivity
import com.example.uwlapp.activities.LoginRegActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import java.util.regex.Pattern

//boolean condition to check if the all data is valid or not
private var booleanTest1 = false
private var booleanTest2 = false
private var booleanTest3 = false
private var booleanTest4 = false
private var booleanTest5 = false
private var booleanTest6 = false
private var booleanTest7 = false
private var booleanTest8 = false


object customconfigs {
    fun textList(email: TextInputEditText, password: TextInputEditText, button: Button) {
        email.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!emailVerification(email.text.toString())) {
                    email.error = "Please enter a valid email"
                    email.requestFocus()
                    button.isEnabled = false
                } else {
                    email.error = null
                    booleanTest1 = true
                    button.background = ContextCompat.getDrawable(
                        email.context,
                        R.drawable.buttonbackground
                    )
                    if (booleanTest1 && booleanTest2) {
                        button.isEnabled = true
                        button.background = ContextCompat.getDrawable(
                            email.context,
                            R.drawable.buttonbackground
                        )
                        button.backgroundTintList = ContextCompat.getColorStateList(button.context, R.color.colorPrimary)

                    } else {
                        button.isEnabled = false
                        button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorPrimaryDark))
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
            //email validation regex function
            private fun emailVerification(email: String?): Boolean {
                val p = Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
//    ***************************************************************************************/
//    *    Title: General Email Regex (RFC 5322 Official Standard)
//    *    Author: Zenbase
//    *    Availability: https://emailregex.com/
//    ***************************************************************************************/

                val m = p.matcher(email);
                booleanTest1 = m.matches()
                return booleanTest1
            }
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        }
        )
        password.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!passVer(password.text.toString())) {
                    password.error = "Please enter a valid password"
                    password.requestFocus()
                    button.isEnabled = false
                } else {
                    password.error = null
                    booleanTest2 = true
                    button.background = ContextCompat.getDrawable(
                        email.context,
                        R.drawable.buttonbackground
                    )
                }
                if (booleanTest1 && booleanTest2) {
                    button.isEnabled = true
                    button.background = ContextCompat.getDrawable(
                        email.context,
                        R.drawable.buttonbackground
                    )
                    button.backgroundTintList = ContextCompat.getColorStateList(button.context, R.color.colorPrimary)
                } else {
                    button.isEnabled = false
                    button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorPrimaryDark))
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
            private fun passVer(password: String?): Boolean {
                val passPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
//    ***************************************************************************************/
//    *    Title: Java regex validate password examples
//    *    Author: mkyong
//    *    Date: 05 November 2020
//    *    Availability: https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
//    ***************************************************************************************/

                val p = Pattern.compile(passPattern)
                val m = p.matcher(password);
                booleanTest2 = m.matches()
                return booleanTest2
            }
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        }
        )
    }




    fun validateFirst(fullname: TextInputEditText, username: TextInputEditText, mobilephone: TextInputEditText, email: TextInputEditText, password: TextInputEditText, password2: TextInputEditText, button: Button) {
        fullname.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!fullnameVerification(fullname.text.toString())) {
                    fullname.error = "Please enter a valid full name"
                    fullname.requestFocus()
                } else {
                    fullname.error = null
                    booleanTest3 = true
                    button.background = ContextCompat.getDrawable(
                        fullname.context,
                        R.drawable.buttonbackground
                    )
                    if (booleanTest3 && booleanTest4 && booleanTest5 && booleanTest6 && booleanTest7 && booleanTest8) {
                        button.isEnabled = true
                        button.background = ContextCompat.getDrawable(
                            fullname.context,
                            R.drawable.buttonbackground
                        )
                        button.backgroundTintList = ContextCompat.getColorStateList(button.context, R.color.colorPrimary)

                    } else {
                        button.isEnabled = false
                        button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorPrimaryDark))
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
            private fun fullnameVerification(fullname: String?): Boolean {
                val p = Pattern.compile("(^[A-Za-z]{3,16})([ ]{0,1})([A-Za-z]{3,16})?([ ]{0,1})?([A-Za-z]{3,16})?([ ]{0,1})?([A-Za-z]{3,16})")
                val m = p.matcher(fullname);
                booleanTest3 = m.matches()
                return booleanTest3
            }
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        })
        username.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!usernameVerification(username.text.toString())) {
                    username.error = "Please enter a valid usernname"
                    username.requestFocus()
                } else {
                    username.error = null
                    booleanTest4 = true
                    button.background = ContextCompat.getDrawable(
                        username.context,
                        R.drawable.buttonbackground
                    )
                    if (booleanTest3 && booleanTest4 && booleanTest5 && booleanTest6 && booleanTest7 && booleanTest8) {
                        button.isEnabled = true
                        button.background = ContextCompat.getDrawable(
                            username.context,
                            R.drawable.buttonbackground
                        )
                        button.backgroundTintList = ContextCompat.getColorStateList(button.context, R.color.colorPrimary)

                    } else {
                        button.isEnabled = false
                        button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorPrimaryDark))
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
            private fun usernameVerification(username: String?): Boolean {
                val p = Pattern.compile("^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]*){6,18}[a-zA-Z0-9]\$")
//    *    Title: Java regex validate username examples
//    *    Author: mkyong
//    *    Date: 05 November 2020
//    *    Availability: https://mkyong.com/regular-expressions/how-to-validate-username-with-regular-expression/
//    ***************************************************************************************/

                val m = p.matcher(username);
                booleanTest4 = m.matches()
                return booleanTest4
            }
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        })

        mobilephone.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val intValue = mobilephone.text.toString()
                if (!mobileVerification(intValue)) {
                    mobilephone.error = "Please enter a valid mobilephone"
                    mobilephone.requestFocus()
                } else {
                    mobilephone.error = null
                    booleanTest5 = true
                    button.background = ContextCompat.getDrawable(
                        mobilephone.context,
                        R.drawable.buttonbackground
                    )
                    if (booleanTest3 && booleanTest4 && booleanTest5 && booleanTest6 && booleanTest7 && booleanTest8) {
                        button.isEnabled = true
                        button.background = ContextCompat.getDrawable(
                            mobilephone.context,
                            R.drawable.buttonbackground
                        )
                        button.backgroundTintList = ContextCompat.getColorStateList(button.context, R.color.colorPrimary)

                    } else {
                        button.isEnabled = false
                        button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorPrimaryDark))
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
            private fun mobileVerification(mobilephone: String?): Boolean {
                val p = Pattern.compile("(07\\d{9}|447\\d{9})\$")
//    ***************************************************************************************/
//    *    UK phone regex
//    *    Author: Regex Lib
//    *    Availability: https://regexlib.com/Search.aspx?k=uk%20telephone
//    ***************************************************************************************/


                val m = p.matcher(mobilephone.toString());
                booleanTest5 = m.matches()
                return booleanTest5
            }
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        })

        email.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!emailVerification(email.text.toString())) {
                    email.error = "Please enter a valid email"
                    email.requestFocus()
                } else {
                    email.error = null
                    booleanTest6 = true
                    button.background = ContextCompat.getDrawable(
                        email.context,
                        R.drawable.buttonbackground
                    )
                    if (booleanTest3 && booleanTest4 && booleanTest5 && booleanTest6 && booleanTest7 && booleanTest8) {
                        button.isEnabled = true
                        button.background = ContextCompat.getDrawable(
                            email.context,
                            R.drawable.buttonbackground
                        )
                        button.backgroundTintList = ContextCompat.getColorStateList(button.context, R.color.colorPrimary)

                    } else {
                        button.isEnabled = false
                        button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorPrimaryDark))
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
            private fun emailVerification(email: String?): Boolean {
                val p = Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
//    ***************************************************************************************/
//    *    Title: General Email Regex (RFC 5322 Official Standard)
//    *    Author: Zenbase
//    *    Availability: https://emailregex.com/
//    ***************************************************************************************/

                val m = p.matcher(email);
                booleanTest6 = m.matches()
                return booleanTest6
            }
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        }
        )

        password.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!passVer(password.text.toString())) {
                    password.error = "Please enter a valid password"
                    password.requestFocus()
                } else {
                    password.error = null
                    booleanTest7 = true
                    button.background = ContextCompat.getDrawable(
                        email.context,
                        R.drawable.buttonbackground
                    )
                }
                if (booleanTest3 && booleanTest4 && booleanTest5 && booleanTest6 && booleanTest7 && booleanTest8) {
                    button.isEnabled = true
                    button.background = ContextCompat.getDrawable(
                        password.context,
                        R.drawable.buttonbackground
                    )
                    button.backgroundTintList = ContextCompat.getColorStateList(button.context, R.color.colorPrimary)
                } else {
                    button.isEnabled = false
                    button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorPrimaryDark))
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
            private fun passVer(password: String?): Boolean {
                val passPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
                //    ***************************************************************************************/
//    *    Title: Java regex validate password examples
//    *    Author: mkyong
//    *    Date: 05 November 2020
//    *    Availability: https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
//    ***************************************************************************************/

                val p = Pattern.compile(passPattern)
                val m = p.matcher(password);
                booleanTest7 = m.matches()
                return booleanTest7
            }
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        }
        )
        password2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//check if both passwords are the same, if so, enable the button
                if (password2.text.toString() == password.text.toString()) {
                    password2.error = null
                    booleanTest8 = true
                    button.background = ContextCompat.getDrawable(
                        password2.context,
                        R.drawable.buttonbackground
                    )
                } else {
                    password2.error = "Passwords do not match"
                    password2.requestFocus()
                }
                if (booleanTest3 && booleanTest4 && booleanTest5 && booleanTest6 && booleanTest7 && booleanTest8) {
                    button.isEnabled = true
                    button.background = ContextCompat.getDrawable(
                        password2.context,
                        R.drawable.buttonbackground
                    )
                    button.backgroundTintList = ContextCompat.getColorStateList(button.context, R.color.colorPrimary)
                } else {
                    button.isEnabled = false
                    button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorPrimaryDark))
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }})}

    fun lightscreen(windowParametrs: AppCompatActivity) {
        val window: Window = windowParametrs.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(windowParametrs, R.color.colorDarkBackground)
        window.navigationBarColor = ContextCompat.getColor(windowParametrs, R.color.colorDarkBackground)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
//    ***************************************************************************************/
//    *    Title: Android lollipop change navigation bar color
//    *    Author: Stack Overflow
//    *    Availability: https://stackoverflow.com/questions/27839105/android-lollipop-change-navigation-bar-color
//    ***************************************************************************************/

    fun smallShow(url: String, imageView: ImageView) {

        imageView.load(url) {
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
            memoryCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
            size(415, 435)
            precision(Precision.EXACT)
            scale(Scale.FILL)
        }
//    ***************************************************************************************/
//    *    Title: An image loading library for Android backed by Kotlin Coroutines
//    *    Author: Coil
//    *    Date: 02 October 2022
//    *    Code Version: 2.2.2
//    *    Availability: https://coil-kt.github.io/coil/
//    ***************************************************************************************/

    }


    fun largeShow(url: String, imageView: ImageView) {

        imageView.load(url) {
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
            memoryCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
            size(850, 850)
            precision(Precision.EXACT)
            scale(Scale.FILL)
//    ***************************************************************************************/
//    *    Title: An image loading library for Android backed by Kotlin Coroutines
//    *    Author: Coil
//    *    Date: 02 October 2022
//    *    Code Version: 2.2.2
//    *    Availability: https://coil-kt.github.io/coil/
//    ***************************************************************************************/

        }
    }
    //redirect to main activity
    fun redirecting(passString: String, imageView: ImageView, context: Context) {
        imageView.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("FRAGMENT", passString)
            context.startActivity(intent)
        }
    }


    fun mediumlargeShow(url: String, imageView: ImageView) {

        imageView.load(url) {
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
            memoryCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
            precision(Precision.EXACT)
            scale(Scale.FILL)
        }
        //    ***************************************************************************************/
//    *    Title: An image loading library for Android backed by Kotlin Coroutines
//    *    Author: Coil
//    *    Date: 02 October 2022
//    *    Code Version: 2.2.2
//    *    Availability: https://coil-kt.github.io/coil/
//    ***************************************************************************************/

    }

    fun supersmallShow(url: String, imageView: ImageView) {

        imageView.load(url) {
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
            memoryCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
            size(300, 300)
            precision(Precision.EXACT)
            scale(Scale.FILL)
//    ***************************************************************************************/
//    *    Title: An image loading library for Android backed by Kotlin Coroutines
//    *    Author: Coil
//    *    Date: 02 October 2022
//    *    Code Version: 2.2.2
//    *    Availability: https://coil-kt.github.io/coil/
//    ***************************************************************************************/

        }
    }
    //create a retrofit object
    fun rtfinstance (): ApiInterface {
        val retrofit = RtfClient.getInstance()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        return apiInterface
    }
//    ***************************************************************************************/
//    *    Title: Simple Retrofit in Android Kotlin.
//    *    Author: Harshita Bambure
//    *    Date: 14 November 2021
//    *    Availability: https://harshitabambure.medium.com/simple-retrofit-in-android-kotlin-5264b2839645
//    ***************************************************************************************/

    @RequiresApi(Build.VERSION_CODES.O)
    //decode the token
    fun tokenconvert(token: String): String {
        val chunks = token!!.split("\\.".toRegex()).toList()
        val decoder: Base64.Decoder = Base64.getUrlDecoder()
        return String(decoder.decode(chunks[1]))
    }
//    ***************************************************************************************/
//    *    Title: Kotlin Base64 Encoding and Decoding
//    *    Author: bezkoder
//    *    Date: 23 February 2020
//    *    Availability: https://www.bezkoder.com/kotlin-base64/
//    ***************************************************************************************/

    //retrieve particular data from the string
    fun substring(str: String, n: Int): String? {
        var lastnChars = str
        if (lastnChars.length > n) {
            lastnChars = lastnChars.substring(lastnChars.length - n, lastnChars.length)
        }
        return lastnChars
    }
    //redirect to the login page
    fun loginredirection(context: Context) {
        val intent = Intent(context, LoginRegActivity::class.java)
        context.startActivity(intent)
    }
    //redirect to the cart activity
    fun cart_redirecton(imageView: ImageView, context: Context) {
        imageView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            context.startActivity(intent)
        })
    }
    //redirect to the main activity
    fun fscreen_redirecton(imageView: ImageView, context: Context) {
        imageView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        })
    }}


fun mobileValidationOnTextChange(mobilephone: TextInputEditText){
    mobilephone.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val p = Pattern.compile("(07\\d{9}|447\\d{9})\$")
//    ***************************************************************************************/
//    *    UK phone regex
//    *    Author: Regex Lib
//    *    Availability: https://regexlib.com/Search.aspx?k=uk%20telephone
//    ***************************************************************************************/


            val m = p.matcher(mobilephone.text.toString());
            if (m.matches()) {
                mobilephone.error = null
            } else {
                mobilephone.error = "Invalid email address"
                mobilephone.requestFocus()
            }}
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }
        override fun afterTextChanged(s: Editable) {
        }
    }
    )
}
fun emailValidationOnTextChange(email: TextInputEditText) {
    email.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val p = Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
//    ***************************************************************************************/
//    *    Title: General Email Regex (RFC 5322 Official Standard)
//    *    Author: Zenbase
//    *    Availability: https://emailregex.com/
//    ***************************************************************************************/
          val m = p.matcher(email.text.toString());
            if (m.matches()) {
                email.error = null
            } else {
                email.error = "Invalid email address"
                email.requestFocus()
            }}
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }
        override fun afterTextChanged(s: Editable) {
        }})}

fun passwordValidationOnTextChange(password: TextInputEditText) {
    password.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
//    ***************************************************************************************/
//    *    Title: Java regex validate password examples
//    *    Author: mkyong
//    *    Date: 05 November 2020
//    *    Availability: https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
//    ***************************************************************************************/

            val m = p.matcher(password.text.toString());
            if (m.matches()) {
                password.error = null
            } else {
                password.error = "Invalid password"
                password.requestFocus()
            }}
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }
        override fun afterTextChanged(s: Editable) {
        }})


}

fun postcodeValidationOnTxtChange(postcode: TextInputEditText){
    postcode.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val p = Pattern.compile("^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([AZa-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z])))) [0-9][A-Za-z]{2})\$")
//    ***************************************************************************************/
//    *    Title: Postcode Validation
//    *    Author: IDDQD Limited
//    *    Availability: https://ideal-postcodes.co.uk/guides/postcode-validation
//    ***************************************************************************************/


            val m = p.matcher(postcode.text.toString());
            if (m.matches()) {
                postcode.error = null
            } else {
                postcode.error = "Invalid post code"
                postcode.requestFocus()
            }}
//    ***************************************************************************************/
//    *    A guide to regular expressions in Kotlin
//    *    Author: Muyiwa Femi-Ige
//    *    Date: 09 June 2022
//    *    Availability: https://blog.logrocket.com/guide-regular-expression-kotlin/
//    ***************************************************************************************/


        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }
        override fun afterTextChanged(s: Editable) {
        }})}

