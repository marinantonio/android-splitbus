/*
 * MIT License
 *
 * Copyright (c) 2013 - 2019 Antonio Marin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.am.stbus.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.am.stbus.models.VozniRed

/**

KOTLIN DATABASE TUTORIAL
https://www.techotopia.com/index.php/A_Kotlin_Android_SQLite_Database_Tutorial

*/

class DatabaseHandler(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 11
        private val DATABASE_NAME = "splitbusDB.db"
        private val TABLE_VOZNI = "vozniredovi"

        private val KEY_ID = "id"
        private val KEY_NAZIV = "naziv"
        private val KEY_WEB_ID = "web"
        private val KEY_GMAPS_ID = "gmapsid"
        private val KEY_TIP_ID = "tip"
        private val KEY_NEDAVNO = "nedavno"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_VOZNI = ("CREATE TABLE " + TABLE_VOZNI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAZIV + " TEXT,"
                + KEY_WEB_ID + " INTEGER," + KEY_GMAPS_ID + " TEXT,"
                + KEY_TIP_ID + " INTEGER," + KEY_NEDAVNO + " INTEGER" + ")")
        db.execSQL(CREATE_TABLE_VOZNI)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_VOZNI")
        // Create tables again
        onCreate(db)
        //updated = true
        Log.w("TaskDBAdapter", "Upgrading from version $oldVersion to $newVersion, which will destroy all old data")
    }

    fun getProfilesCount(): Int {
        val countQuery = "SELECT  * FROM $TABLE_VOZNI"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun isTableEmpty(): Boolean {
        val flag: Boolean
        val quString = "SELECT EXISTS(SELECT 1 FROM $TABLE_VOZNI)"
        val db = readableDatabase
        val cursor = db.rawQuery(quString, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        flag = count != 1
/*        if (count == 1) {
            flag =  false;
        } else {
            flag = true;
        }*/
        cursor.close()
        db.close()
        return flag
    }

    fun addFavorites(web: Int): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_NEDAVNO, 1) // vozni red

        return db.update(TABLE_VOZNI, values, "$KEY_WEB_ID = ?", arrayOf(web.toString()))
    }


    fun removeFavorites(web: Int): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NEDAVNO, 0) // vozni red
        return db.update(TABLE_VOZNI, values, "$KEY_WEB_ID = ?", arrayOf(web.toString()))
    }


    fun addVozniRed(voznired: VozniRed) {
        val db = this.writableDatabase
        val values = ContentValues()
        try {
            db.beginTransaction()
            values.put(KEY_NAZIV, voznired.naziv)
            values.put(KEY_WEB_ID, voznired.web)
            values.put(KEY_GMAPS_ID, voznired.gmaps)
            values.put(KEY_TIP_ID, voznired.tip)
            values.put(KEY_NEDAVNO, voznired.nedavno)
            db.insert(TABLE_VOZNI, null, values)
            db.setTransactionSuccessful()
            //db.close()
        } finally {
            db.endTransaction()
        }
    }

    fun getVozniRed(id: Int): List<VozniRed> {
        val data = ArrayList<VozniRed>()
        val db = this.readableDatabase
        val selectQuery = ("SELECT  * FROM " + TABLE_VOZNI + " WHERE "
                + KEY_WEB_ID + " = " + id)
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val voznired = VozniRed(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)))
                data.add(voznired)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return data
    }


    val nedavnoList: ArrayList<VozniRed>
        get() {
            val vozniRedovi = ArrayList<VozniRed>()
            val selectedQuery = "SELECT * FROM $TABLE_VOZNI WHERE $KEY_NEDAVNO = 1"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectedQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val voznired = VozniRed(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                            Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                            Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)))
                    vozniRedovi.add(voznired)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return vozniRedovi
        }


    val gradSplit: ArrayList<VozniRed>
        get() {
            val vozniRedovi = ArrayList<VozniRed>()
            val selectQuery = "SELECT  * FROM $TABLE_VOZNI WHERE $KEY_TIP_ID = 1"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val voznired = VozniRed(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                            Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                            Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)))
                    vozniRedovi.add(voznired)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return vozniRedovi
        }


    val urbanoPodrucje: ArrayList<VozniRed>
        get() {
            val vozniRedovi = ArrayList<VozniRed>()
            val selectQuery = "SELECT  * FROM $TABLE_VOZNI WHERE $KEY_TIP_ID = 2"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val voznired = VozniRed(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                            Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                            Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)))
                    vozniRedovi.add(voznired)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return vozniRedovi
        }


    val prigradskoPodrucje: ArrayList<VozniRed>
        get() {
            val vozniRedovi = ArrayList<VozniRed>()
            val selectQuery = "SELECT  * FROM $TABLE_VOZNI WHERE $KEY_TIP_ID = 3"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val voznired = VozniRed(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                            Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                            Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)))
                    vozniRedovi.add(voznired)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return vozniRedovi
        }


    val trogirSolta: ArrayList<VozniRed>
        get() {
            val vozniRedovi = ArrayList<VozniRed>()
            val selectQuery = "SELECT  * FROM $TABLE_VOZNI WHERE $KEY_TIP_ID = 4 OR $KEY_TIP_ID = 5"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val voznired = VozniRed(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                            Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                            Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)))
                    vozniRedovi.add(voznired)
                } while (cursor.moveToNext())
            }
            cursor.close()
            db.close()
            return vozniRedovi
        }
}


