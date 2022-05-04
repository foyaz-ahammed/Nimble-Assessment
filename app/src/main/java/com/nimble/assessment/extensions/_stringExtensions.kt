package com.nimble.assessment.extensions

import com.nimble.assessment.repository.entities.Response

/**
 * @return get address string from [Response.PharmacyDetail]
 */
fun Response.PharmacyDetail.getFormattedAddress(): String {
    val address = this.value.address
    return address.streetAddress1 + ", " + address.city + ", " + address.usTerritory + ", " + address.postalCode
}