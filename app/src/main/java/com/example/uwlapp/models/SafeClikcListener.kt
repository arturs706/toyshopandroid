package com.example.uwlapp.models

import android.os.SystemClock
import android.view.View
//class for a safe click listener, prevents double clicks on buttons causing bugs
class SafeClickListener(private var defaultInterval: Int = 759, private val onSafeCLick: (View) -> Unit) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun View.changedListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}
//    ***************************************************************************************/
//    *    Title: Avoid button multiple rapid clicks
//    *    Author: Stack Overflow
//    *    Date: March 2013
//    *    Availability: https://stackoverflow.com/questions/16534369/avoid-button-multiple-rapid-clicks
//    ***************************************************************************************/


