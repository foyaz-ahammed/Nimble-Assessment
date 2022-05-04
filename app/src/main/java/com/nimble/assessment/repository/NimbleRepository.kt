package com.nimble.assessment.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nimble.assessment.extensions.getJsonDataFromAsset
import com.nimble.assessment.repository.apis.PharmacyAPI
import com.nimble.assessment.repository.entities.DataResult
import com.nimble.assessment.repository.entities.Response
import com.nimble.assessment.util.Constants
import java.net.URL

/**
 * Repository class
 */
class NimbleRepository(private val context: Context, private val api: PharmacyAPI) {
    /**
     * Load pharmacy list from assets json file
     */
    fun loadPharmacyListFromJson(): List<Response.SimplePharmacy> {
        val text = context.getJsonDataFromAsset(Constants.PHARMACY_JSON_FILE)
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

    /**
     * Load pharmacy info through network call
     * @return [DataResult.SUCCESS] or [DataResult.FAILURE]
     */
    suspend fun fetchPharmacyDetail(pharmacyId: String): DataResult<Response.PharmacyDetail> =
        try {
            val pharmacyInfo = api.getPharmacyDetail(pharmacyId)
            DataResult.SUCCESS(pharmacyInfo)
        } catch (e: Exception) {
            DataResult.FAILURE(e)
        }

    /**
     * Load medications list content (Text separated by comma)
     */
    fun loadMedicationsText(callback: (result: DataResult<String>) -> Unit) {
        val thread = Thread {
            try {
                val text = URL(Constants.MEDICATIONS_URL).readText()
                callback.invoke(DataResult.SUCCESS(text))
            } catch (e: Exception) {
                callback.invoke(DataResult.FAILURE(e))
            }
        }

        thread.start()
    }
}