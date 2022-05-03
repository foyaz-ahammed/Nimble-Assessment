package com.nimble.assessment.extensions

import android.content.Context
import java.io.IOException

/**
 * @return [String] data from asset file
 */
fun Context.getJsonDataFromAsset(fileName: String): String? {
    val jsonString: String
    try {
        jsonString = this.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }

    return jsonString
}