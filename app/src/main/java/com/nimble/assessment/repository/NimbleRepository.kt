package com.nimble.assessment.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nimble.assessment.database.NimbleDatabase
import com.nimble.assessment.database.entities.Order
import com.nimble.assessment.extensions.getJsonDataFromAsset
import com.nimble.assessment.repository.apis.PharmacyAPI
import com.nimble.assessment.repository.entities.DataResult
import com.nimble.assessment.repository.entities.Response
import com.nimble.assessment.util.Constants
import com.nimble.assessment.util.ensureBackgroundThread
import java.net.URL

/**
 * Repository class
 */
class NimbleRepository(private val context: Context, private val api: PharmacyAPI, private val nimbleDatabase: NimbleDatabase) {
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
        ensureBackgroundThread {
            try {
                val text = URL(Constants.MEDICATIONS_URL).readText()
                callback.invoke(DataResult.SUCCESS(text))
            } catch (e: Exception) {
                callback.invoke(DataResult.FAILURE(e))
            }
        }
    }

    /**
     * Add list of orders
     */
    suspend fun orderMedications(list: List<Order>) {
        nimbleDatabase.orderDao().addOrderList(list)
    }

    suspend fun getOrderList() = nimbleDatabase.orderDao().getOrderList()
}