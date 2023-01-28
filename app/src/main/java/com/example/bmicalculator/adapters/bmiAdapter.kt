package com.example.bmicalculator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmicalculator.R
import com.example.bmicalculator.models.Users

class bmiAdapter (private val bmiList:ArrayList<Users>) :
    RecyclerView.Adapter<bmiAdapter.ViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bmi_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentbmi = bmiList[position]
        holder.tvbmiName.text = currentbmi.bmiName
    }

    override fun getItemCount(): Int {
        return bmiList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvbmiName : TextView = itemView.findViewById(R.id.tvbmiName)
    }

}