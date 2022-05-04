package com.nimble.assessment.repository.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Network/Local Response class
 */
class Response {
    data class Pharmacies(
        val pharmacies: List<SimplePharmacy>?
    )
    data class SimplePharmacy(
        var name: String? = null,
        var pharmacyId: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class PharmacyDetail (
        @Json(name = "details") val details: String,
        @Json(name = "href") val href: String,
        @Json(name = "responseCode") val responseCode: String,
        @Json(name = "value") val value: PharmacyValue
    )

    @JsonClass(generateAdapter = true)
    data class PharmacyValue (
        @Json(name = "acceptInvalidAddress") val acceptInvalidAddress: Boolean,
        @Json(name = "active") val active: Boolean,
        @Json(name = "address") val address: Address,
        @Json(name = "checkoutPharmacy") val checkoutPharmacy: Boolean,
        @Json(name = "defaultTimeZone") val defaultTimezone: String?,
        @Json(name = "deliverableStates") val deliverableStates: List<String>,
        @Json(name = "deliverySubsidyAmount") val deliverySubsidyAmount: Int?,
        @Json(name = "id") val id: String,
        @Json(name = "importActive") val importActive: Boolean,
        @Json(name = "localId") val localId: String,
        @Json(name = "marketplacePharmacy") val marketplacePharmacy: Boolean,
        @Json(name = "name") val name: String,
        @Json(name = "pharmacistInCharge") val pharmacistInCharge: String?,
        @Json(name = "pharmacyChainId") val pharmacyChainId: String,
        @Json(name = "pharmacyHours") val pharmacyHours: String?,
        @Json(name = "pharmacyLoginCode") val pharmacyLoginCode: String?,
        @Json(name = "pharmacyType") val pharmacyType: String,
        @Json(name = "postalCodes") val postalCodes: String?,
        @Json(name = "primaryPhoneNumber") val primaryPhoneNumber: String?,
        @Json(name = "testPharmacy") val testPharmacy: Boolean
    )

    @JsonClass(generateAdapter = true)
    data class Address (
        @Json(name = "addressType") val addressType: String,
        @Json(name = "city") val city: String,
        @Json(name = "externalId") val externalId: String,
        @Json(name = "googlePlaceId") val googlePlaceId: String?,
        @Json(name = "isValid") val isValid: Boolean,
        @Json(name = "latitude") val latitude: Double,
        @Json(name = "longitude") val longitude: Double,
        @Json(name = "postalCode") val postalCode: String,
        @Json(name = "streetAddress1") val streetAddress1: String,
        @Json(name = "usTerritory") val usTerritory: String
    )
}