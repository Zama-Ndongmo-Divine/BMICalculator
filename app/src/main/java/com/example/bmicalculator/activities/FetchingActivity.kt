package com.example.bmicalculator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bmicalculator.R
import com.example.bmicalculator.adapters.BMI
import com.example.bmicalculator.database.DBHelper

/**
 * Get data form the SQLite database and displays it as a list in an app window
 */
class  FetchingActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var adapter: BMI? = null
    private lateinit var dbhelper: DBHelper

    /**
     *Creates an instance of the Fetching activity activity
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_info)


        recyclerView = findViewById(R.id.recyclerView)
        initRecyclerView()
        getBMIData()


        adapter?.setOnClickDeleteItem {
            deleteData(it.id)
        }


    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BMI()
        recyclerView.adapter = adapter
    }

    private fun getBMIData() {

        dbhelper = DBHelper(this)

        val BMIlist = dbhelper.getAllBMI()
        Log.e("Data", "${BMIlist.size}")
        adapter?.addItems(BMIlist)
    }

    private fun deleteData(id:Int){
       // if (id == null) return

        val builder = AlertDialog.Builder(this)

        builder.setMessage("Are you sure you want to delete record?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            dbhelper.deleteBMIById(id)
            getBMIData()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

}

