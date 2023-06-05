package com.example.bmicalculator.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.bmicalculator.R
import com.example.bmicalculator.models.Users


class BMI : RecyclerView.Adapter<BMI.BMIViewHolder>() {
    private var BMIList: ArrayList<Users> = ArrayList()
    private var onClickDeleteItem: ((Users) -> Unit)? = null

    fun addItems(items: ArrayList<Users>) {
        this.BMIList = items
        notifyDataSetChanged()
    }

    fun setOnClickDeleteItem(callback:(Users) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = BMIViewHolder(
        LayoutInflater.from(p0.context).inflate(R.layout.activity_fetching, p0, false)
    )

    override fun onBindViewHolder(p0: BMIViewHolder, p1: Int) {
       val BMI = BMIList[p1]
        p0.bindView(BMI)
        p0.btnDel.setOnClickListener { onClickDeleteItem?.invoke(BMI) }
    }

    override fun getItemCount(): Int {
        return BMIList.size
    }

    class BMIViewHolder(var view: View): RecyclerView.ViewHolder(view) {

        private var id = view.findViewById<TextView>(R.id.svid)
        private var Name = view.findViewById<TextView>(R.id.svName)
        private var BMI = view.findViewById<TextView>(R.id.svBMI)
        var btnDel = view.findViewById<Button>(R.id.svDel)


        fun bindView(bmi: Users) {
            id.text = bmi.id.toString()
            Name.text = bmi.Name
            BMI.text = bmi.BMI
        }
    }
}