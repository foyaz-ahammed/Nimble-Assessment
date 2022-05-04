package com.nimble.assessment.repository.entities

import java.lang.Exception

/**
 * Data result with the status of success and failure
 */
sealed class DataResult<T> {
    data class SUCCESS<T>(val data: T): DataResult<T>()
    data class FAILURE<T>(val exception: Exception): DataResult<T>()
}