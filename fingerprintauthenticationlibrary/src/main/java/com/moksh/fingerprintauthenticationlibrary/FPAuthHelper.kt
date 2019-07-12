package com.moksh.fingerprintauthenticationlibrary

import android.content.Context
import android.os.Build
import android.os.Handler
import android.support.v4.os.CancellationSignal
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.security.PrivateKey

class FPAuthHelper(
    private val context: Context,
    private val fpAuthCallback: FPAuthCallback,
    private val fingerprintNotSupportedCallback: FingerprintNotSupportedCallback,
    private val dialogView: View) {
    private var cancellationSignal: CancellationSignal? = null
    private var handler: Handler = Handler()
    private val DELAY: Long = 1200

    private val returnToScanning = Runnable {
        dialogView.findViewById<TextView>(R.id.fingerprint_dialog_status).text = "Touch the Sensor"
        dialogView.findViewById<ImageView>(R.id.fingerprint_dialog_icon)
            .setImageResource(R.drawable.ic_fingerprint_black_24dp)
    }

    private fun checkRequiredConditionsForFingerprint(context: Context): Boolean {
        // Check if the Android Version is above or equal to MarshMallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val fingerprintManagerCompat = FingerprintManagerCompat.from(context)

            // Check if the device has a Fingerprint Scanner
            if (!fingerprintManagerCompat.isHardwareDetected) {
                fpAuthCallback.onNoFingerprintScannerAvailable()
                fingerprintNotSupportedCallback.onNoFingerprintScannerAvailable()
                return false
            }

            // Check if the device has registered fingerprints
            if (!fingerprintManagerCompat.hasEnrolledFingerprints()) {
                fpAuthCallback.onNoFingerprintRegistered()
                fingerprintNotSupportedCallback.onNoFingerprintRegistered()
                return false
            }

            return true
        } else {
            // Android Version is below Mashmallow
            fpAuthCallback.onBelowAndroidMarshmallow()
            fingerprintNotSupportedCallback.onBelowAndroidMarshmallow()
            return false
        }
    }

    fun startAuth() {
        // It will require fingerPrintManager and cryptoObject
        if (!checkRequiredConditionsForFingerprint(context)) return
        val fingerprintManager: FingerprintManagerCompat = FingerprintManagerCompat.from(context)
        cancellationSignal = CancellationSignal()
        fingerprintManager.authenticate(null, 0, cancellationSignal,
            object: FingerprintManagerCompat.AuthenticationCallback() {
                override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errMsgId, errString)
                    //fpAuthCallback.onFingerprintAuthFailed(errString.toString())
                }

                override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                    super.onAuthenticationHelp(helpMsgId, helpString)
                    fpAuthCallback.onFingerprintAuthFailed(helpString.toString())
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    dialogView.findViewById<ImageView>(R.id.fingerprint_dialog_icon).
                        setImageResource(R.drawable.ic_cancel_black_24dp)
                    dialogView.findViewById<TextView>(R.id.fingerprint_dialog_status).text = "Fingerprint Not Recognized"
                    handler.postDelayed(returnToScanning, DELAY)
                    fpAuthCallback.onFingerprintAuthFailed("Fingerprint not recognized.")
                }

                override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    dialogView.findViewById<ImageView>(R.id.fingerprint_dialog_icon).
                        setImageResource(R.drawable.ic_check_circle_black_24dp)
                    dialogView.findViewById<TextView>(R.id.fingerprint_dialog_status).text = "Authentication Successful"
                    handler.postDelayed(Runnable {
                        fingerprintNotSupportedCallback.onSuccess()
                        fpAuthCallback.onFingerprintAuthSuccess("Authentication Successful")
                    }, 500)

                }

            }, null)
    }

    fun stopAuth() {
        cancellationSignal?.let {
            cancellationSignal!!.cancel()
            cancellationSignal = null
        }
    }
}