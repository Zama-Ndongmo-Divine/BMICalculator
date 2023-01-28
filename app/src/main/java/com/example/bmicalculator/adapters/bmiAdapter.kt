package com.example.bmicalculator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmicalculator.R
import com.example.bmicalculator.models.Users

/**
 * An adapter. Creates on view holders to hold the taken database information in a window for proper visibility.
 */
class bmiAdapter (private val bmiList:ArrayList<Users>) :
    RecyclerView.Adapter<bmiAdapter.ViewHolder> () {

    /**
     * creates a new viewHolder and initializes some private fields to be used by the recycleView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bmi_list_item, parent, false)
        return ViewHolder(itemView)
    }

    /**
     * updates viewHolder contents with the name and bmi results at the top of the window
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currentbmi = bmiList[position]
        holder.tvbmiName.text = currentbmi.bmiName
    }

    /**
     * @return Returns the total number of names in the data set held by the adapter.
     */
    override fun getItemCount(): Int {
        return bmiList.size
    }

    /**
     *
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvbmiName : TextView = itemView.findViewById(R.id.tvbmiName)
    }

}