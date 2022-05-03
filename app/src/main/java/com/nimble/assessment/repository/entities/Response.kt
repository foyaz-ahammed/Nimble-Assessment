package com.nimble.assessment.repository.entities

/**
 * Network/Local Response class
 */
class Response {
    data class Pharmacies(
        val pharmacies: List<SimplePharmacy>? = emptyList()
    )
    data class SimplePharmacy(
        var name: String? = null,
        var pharmacyId: String? = null
    )
}