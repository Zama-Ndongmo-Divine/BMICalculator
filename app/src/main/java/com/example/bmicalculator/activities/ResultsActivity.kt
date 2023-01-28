package com.example.bmicalculator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bmicalculator.R

/**
 * Displays the results of each session on a separate window
 * this data is also stored in a database and can be accessed at any time
 */
class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
    }
}