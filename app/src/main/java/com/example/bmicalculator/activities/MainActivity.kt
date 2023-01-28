package com.example.bmicalculator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bmicalculator.R
import com.example.bmicalculator.models.Users
import com.example.bmicalculator.databinding.ActivityMainBinding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)
        val calcButton = findViewById<Button>(R.id.btnCalculate)
        val uName = findViewById<EditText>(R.id.name)
        dbRef = FirebaseDatabase.getInstance().getReference("BMI Results")


        calcButton.setOnClickListener{
            val weight = weightText.text.toString()
            val height = heightText.text.toString()
            val bmiName = uName.text.toString()
            val bmi2dp : Float
            val bmi : Float

            if(vaidateInput(weight, height, bmiName)) {
                bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                //result to 2dp
                bmi2dp = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2dp)
                var bmiID = dbRef.push().key!!
                var Users = Users(bmiID, bmiName, bmi2dp)
                dbRef.child(bmiID).setValue(Users).addOnCompleteListener {
                    Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()

                }
            }


        }
    }



    private fun vaidateInput(weight:String?, height:String?, bmiName:String?):Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is empty!", Toast.LENGTH_LONG).show()
                return false
            }
            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is empty!", Toast.LENGTH_LONG).show()
                return false
            }
            bmiName.isNullOrEmpty() -> {
                Toast.makeText(this, "Input your name!", Toast.LENGTH_LONG).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun displayResult (bmi:Float) {
        val resultIndex = findViewById<TextView>(R.id.tvindex)
        val resultDescription = findViewById<TextView>(R.id.tvResult)
        val info = findViewById<TextView>(R.id.tvinfo)

        resultIndex.text = bmi.toString()
        info.text = "(Normal range is 18.5 - 24.9)"

        var resultText = ""
        var color = 0

        when {
            bmi<18.50 -> {
                resultText = "Underweight"
                color = R.color.underweight
            }
            bmi in 18.50..24.99 -> {
                resultText = "Healthy"
                color = R.color.normal
            }
            bmi in 25.00..29.99 -> {
                resultText = "Overweight"
                color = R.color.overweight
            }
            bmi>30.00 -> {
                resultText = "Obese"
                color = R.color.obese
            }
        }

        resultDescription.setTextColor(ContextCompat.getColor(this, color))
        resultDescription.text = resultText
    }
}