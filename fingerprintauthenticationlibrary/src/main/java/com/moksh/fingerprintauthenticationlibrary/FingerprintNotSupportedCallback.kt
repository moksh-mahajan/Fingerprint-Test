package com.moksh.fingerprintauthenticationlibrary

interface FingerprintNotSupportedCallback {
    fun onBelowAndroidMarshmallow()
    fun onNoFingerprintScannerAvailable()
    fun onNoFingerprintRegistered()
    fun onSuccess()
}