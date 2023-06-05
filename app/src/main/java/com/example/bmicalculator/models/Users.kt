package com.example.bmicalculator.models

import java.util.*

/**
 * Data class with the user model.
 */
data class  Users(
   var id: Int = getAutoId(),
    var Name: String = "Name: ",
    var BMI: String = "BMI: ",

    ) {
    companion object {
        fun getAutoId(): Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}