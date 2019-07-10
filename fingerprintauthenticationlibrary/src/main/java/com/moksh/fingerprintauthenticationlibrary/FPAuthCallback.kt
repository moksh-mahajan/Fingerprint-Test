package com.moksh.fingerprintauthenticationlibrary

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

interface FPAuthCallback {
    fun onBelowAndroidMarshmallow()
    fun onNoFingerprintScannerAvailable()
    fun onNoFingerprintRegistered()
    fun onFingerprintAuthSuccess(message: String)
    fun onFingerprintAuthFailed(errorMessage: String)
    fun onCancel()
}