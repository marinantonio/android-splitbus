/*
 * MIT License
 *
 * Copyright (c) 2013 - 2018 Antonio Marin
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

