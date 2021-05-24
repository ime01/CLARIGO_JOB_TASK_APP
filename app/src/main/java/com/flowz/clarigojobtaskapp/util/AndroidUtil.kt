package com.flowz.byteworksjobtask.util

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream
import java.io.InputStream


fun TextInputEditText.takeWords() : String{
    return this.text.toString().trim()
}
fun EditText.takeWords() : String{
    return this.text.toString().trim()
}

fun clearTexts(views: Array<TextInputEditText>) {
    views.forEach {
        it.text?.clear()
    }
}

fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        inContext.getContentResolver(),
        inImage,
        "Title",
        null
    )
    return Uri.parse(path)
}
fun playAnimation(context: Context, int: Int, view: View) {

        val animation = AnimationUtils.loadAnimation(context, int)
        view.startAnimation(animation)
    }

fun showToast(string: String, context: Context) {

        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

fun showSnackbar(view: View, string: String) {

        Snackbar.make(view, string, Snackbar.LENGTH_LONG).show()
    }

