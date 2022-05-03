package com.nimble.assessment.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nimble.assessment.extensions.getJsonDataFromAsset
import com.nimble.assessment.repository.entities.Response

/**
 * Repository class
 */
class NimbleRepository(private val context: Context) {
    fun loadPharmacyListFromJson(): List<Response.SimplePharmacy> {
        val text = context.getJsonDataFromAsset("pharmacy_list.json")
        text?.let {
            val gson = Gson()
            val pharmacies = gson.fromJson<Response.Pharmacies>(
                it,
                object : TypeToken<Response.Pharmacies>() {}.type
            )
            if (pharmacies != null) {
                return pharmacies.pharmacies ?: emptyList()
            }
        }

        return emptyList()
    }
}