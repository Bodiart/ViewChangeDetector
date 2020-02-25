package com.bodiart.viewchangedetector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bodiart.view_change_detector.ViewChangeDetector
import com.bodiart.view_change_detector.ViewChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewChangeDetect()
    }

    private fun initViewChangeDetect() {
        ViewChangeDetector(this)
            .startDetecting(input1, input2, input3, input4, input5)
    }

    override fun changeDetected() {
        Toast.makeText(this, "changeDetected", Toast.LENGTH_SHORT).show()
        save_btn.isEnabled = true
    }

    override fun changeCancelled() {
        Toast.makeText(this, "changeCancelled", Toast.LENGTH_SHORT).show()
        save_btn.isEnabled = false
    }
}
