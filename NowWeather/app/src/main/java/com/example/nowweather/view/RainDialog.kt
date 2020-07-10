package com.example.nowweather.view

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.example.nowweather.R
import kotlinx.android.synthetic.main.dialog_rain.*

class RainDialog (context: Context) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_rain)
        setCancelable(false)

        val wm = window!!.attributes
        wm.copyFrom(window!!.attributes)
        wm.width = android.view.WindowManager.LayoutParams.MATCH_PARENT
        wm.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT

        btnOk.setOnClickListener {
            dismiss()
        }
    }
}