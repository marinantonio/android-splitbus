package com.am.stbus.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.*

class StorageProvider (var context: Context?) {

    fun saveBitmap(bitmap: Bitmap?, imageName: String) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = context!!.openFileOutput("$imageName.png", Context.MODE_PRIVATE)
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fileOutputStream?.close()
        }
    }

    fun loadBitmap(picName: String): Bitmap? {
        var bitmap: Bitmap? = null
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = context!!.openFileInput("$picName.png")
            bitmap = BitmapFactory.decodeStream(fileInputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fileInputStream?.close()
        }
        return bitmap
    }


    fun checkIfExists(pictureName: String): Boolean {
        var fileInputStream: FileInputStream? = null
        var boolean = false
        val path = context!!.filesDir.absolutePath
        Log.v("PATH", path)
        val file = File("$path/$pictureName.png")
        if (file.exists()){
            boolean = true
        }
        return boolean
    }
}

/*
        var boolean: Boolean = false
        var bitmap: Bitmap? = null
        var fileInputStream: FileInputStream? = null
        try {
            boolean = true
            fileInputStream = context!!.openFileInput("$pictureName.png")
            bitmap = BitmapFactory.decodeStream(fileInputStream)
        } catch (e: FileNotFoundException) {
            //boolean = false
            Log.v("NE POSTOJI", "VALJD")
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fileInputStream?.close()
        }
        return boolean
*/

//        File file = new File(filePath);
//        if(file.exists())


       /* var bitmap = StorageProvider.
        var bitmap: Bitmap? = null
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = context!!.openFileInput("$pictureName.png")
            bitmap = BitmapFactory.decodeStream(fileInputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fileInputStream?.close()
        }

        return bitmap


        val file = StorageProvider.loadBitmap(
        var bitmap: Bitmap? = null

        return file != null && file.exists()
    } */

