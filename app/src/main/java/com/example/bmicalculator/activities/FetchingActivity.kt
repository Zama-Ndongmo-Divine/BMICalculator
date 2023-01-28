package com.example.bmicalculator.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bmicalculator.R
import com.example.bmicalculator.adapters.bmiAdapter
import com.example.bmicalculator.models.Users
import com.google.firebase.database.*

/**
 * Get data form the Realtime firebase database and displays it as a list in an app window
 */
class FetchingActivity : AppCompatActivity() {

    private lateinit var bmiRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var bmiList: ArrayList<Users>
    private lateinit var dbRef: DatabaseReference

    /**
     * Creates an instance of the Fetching activity activity
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        bmiRecyclerView = findViewById(R.id.recyclerView2)
        bmiRecyclerView.layoutManager = LinearLayoutManager(this)
        bmiRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvloadingData)

        bmiList = arrayListOf<Users>()

        getbmiData()
    }

    /**
     * Get the BMI data and place it in a Recycle view
     */
    private fun getbmiData() {
        bmiRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef =FirebaseDatabase.getInstance().getReference("BMI Results")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                bmiList.clear()
                if (snapshot.exists()){
                    for (bmiSnap in snapshot.children) {
                        val bmiData = bmiSnap.getValue(Users::class.java)
                        bmiList.add(bmiData!!)
                    }
                    val mAdapter =bmiAdapter(bmiList)
                    bmiRecyclerView.adapter = mAdapter
                    bmiRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }


            /**
             *  @
             */
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}