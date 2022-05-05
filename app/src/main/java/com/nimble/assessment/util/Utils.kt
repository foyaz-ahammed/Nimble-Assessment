package com.nimble.assessment.util

import android.os.Looper

/**
 * If callback is on ui thread, run the callback through thread, otherwise run  immediately
 */
fun ensureBackgroundThread(callback: () -> Unit) {
    if (isOnMainThread()) {
        Thread {
            callback()
        }.start()
    } else {
        callback()
    }
}

/**
 * Return the looper is on ui thread
 */
fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()
