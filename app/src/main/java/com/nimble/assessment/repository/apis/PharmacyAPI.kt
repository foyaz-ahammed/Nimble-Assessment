package com.nimble.assessment.repository.apis

import com.nimble.assessment.repository.entities.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * API interface defining methods for network calls
 */
interface PharmacyAPI {
    @GET("pharmacies/info/{pharmacyId}")
    suspend fun getPharmacyDetail(
        @Path("pharmacyId") pharmacyId: String
    ): Response.PharmacyDetail
}