package com.example.bmicalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelectionScreen : AppCompatActivity() {

    private lateinit var BMIC: Button
    private lateinit var Hist: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_screen)

        BMIC = findViewById(R.id.BMICalc)
        Hist = findViewById(R.id.Hist)

        BMIC.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        Hist.setOnClickListener {
            val intent = Intent(this, ResultsActivity::class.java)
            startActivity(intent)
        }
    }
}