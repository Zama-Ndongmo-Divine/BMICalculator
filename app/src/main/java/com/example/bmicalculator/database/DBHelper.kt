package com.example.bmicalculator.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bmicalculator.models.Users

class DBHelper (context:Context) : SQLiteOpenHelper (context, DB_NAME, null, DB_VER) {

    companion object {

        private const val DB_VER = 1
        private const val DB_NAME = "BMI3.db"
        private const val TBL_NAME = "BMI_USER"
        private const val ID = "id"
        private const val NAME = "Name"
        private const val BMI = "BMI"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblBMI = ("CREATE TABLE " + TBL_NAME + "("
                + ID + " INTEGER,"
                + NAME + " TEXT,"
                + BMI + " TEXT"
                + ")"
                )
        db?.execSQL(createTblBMI)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_NAME")

        onCreate(db)
    }

    fun insertBMI(std: Users): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.Name)
        contentValues.put(BMI, std.BMI)

        val success = db.insert(TBL_NAME, null, contentValues)
        db.close()
        return success
    }

    fun deleteBMIById(id:Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
         val success = db.delete(TBL_NAME, "ID="+id, null)
        db.close()

        return success
    }

    @SuppressLint("Range")
    fun getAllBMI(): ArrayList<Users>{
        val BMIList: ArrayList<Users> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var Name: String
        var BMI: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                Name = cursor.getString(cursor.getColumnIndex("Name"))
                BMI = cursor.getString(cursor.getColumnIndex("BMI"))

                val std = Users(id = id, Name = Name, BMI = BMI)
                BMIList.add(std)
            }while (cursor.moveToNext())
        }

        return BMIList
    }
}