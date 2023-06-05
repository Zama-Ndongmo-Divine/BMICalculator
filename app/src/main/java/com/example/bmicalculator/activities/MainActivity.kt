package com.example.bmicalculator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.bmicalculator.R
import com.example.bmicalculator.database.DBHelper
import com.example.bmicalculator.models.Users
import com.example.bmicalculator.databinding.ActivityMainBinding


/**
 * This is the main BMI Calculator screen. It contains the BMI calculator, where calculations can be made and stored in a database, together with the username
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dbhelper: DBHelper



    /**
     * Creates an instance of the BMI Calculator activity
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)
        val calcButton = findViewById<Button>(R.id.btnCalculate)
        val uName = findViewById<EditText>(R.id.name)
        val saveButton = findViewById<Button>(R.id.btnSave)

       // var bmiName = uName.text.toString()
        var bmi : Float

        var bmi2dp : Float = 0.0F



        dbhelper = DBHelper(this)

        calcButton.setOnClickListener{

            var weight = weightText.text.toString()
            var height = heightText.text.toString()

            if(vaidateInput(weight, height)) {
                bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                //result to 2dp
                bmi2dp = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2dp)

                val BMI = Users(Name = uName.text.toString(), BMI = bmi2dp.toString())
                val status = dbhelper.insertBMI(BMI)

                // check if data has been inserted

                if (status > -1) {
                    Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show()
                }

            }


        }

    }

    /**
     * This verifies if the user has input a correct @param for the weight, height and name on their various text fields before proceeding with the calculations
     */

    private fun vaidateInput(weight:String?, height:String?):Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is empty!", Toast.LENGTH_LONG).show()
                return false
            }
            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is empty!", Toast.LENGTH_LONG).show()
                return false
            }
          //  bmiName.isNullOrEmpty() -> {
          //      Toast.makeText(this, "Input your name!", Toast.LENGTH_LONG).show()
          //      return false
          //  }
            else -> {
                return true
            }
        }
    }

    /**
     * Displays the result to the user as a float, together with their health status, which ranges from underweight to obese
     */
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