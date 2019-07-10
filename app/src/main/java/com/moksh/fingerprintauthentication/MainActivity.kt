package com.moksh.fingerprintauthentication

import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.moksh.fingerprintauthenticationlibrary.FPAuthCallback
import com.moksh.fingerprintauthenticationlibrary.FPAuthDialog
import com.moksh.fingerprintauthenticationlibrary.FPAuthHelper
import kotlinx.android.synthetic.main.activity_main.*
//import me.aflak.libraries.callback.FingerprintDialogCallback
//import me.aflak.libraries.callback.FingerprintDialogSecureCallback
//import me.aflak.libraries.dialog.FingerprintDialog
//import me.aflak.libraries.utils.FingerprintToken

class MainActivity : AppCompatActivity() {
    //private lateinit var mFingerPrintAuthHelper: FingerPrintAuthHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val fpAuthHelper = FPAuthHelper(this, object: FPAuthCallback{
//            override fun onBelowAndroidMarshmallow() {
//
//            }
//
//            override fun onNoFingerprintScannerAvailable() {
//
//            }
//
//            override fun onNoFingerprintRegistered() {
//
//            }
//
//            override fun onFingerprintAuthSuccess(message: String) {
//                Toast.makeText(this@MainActivity, "Authenticated", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onFingerprintAuthFailed(errorMessage: String) {
//                Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_LONG).show()
//            }
//
//        }).startAuth()
//       FingerprintDialog.initialize(this)
//           .title("abc")
//           .message("Message")
//           .callback(object: FingerprintDialogCallback {
//               override fun onAuthenticationSucceeded() {
//                   Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
//
//               }
//
//               override fun onAuthenticationCancel() {
//                   Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_LONG).show()
//               }
//
//           }).show()

//         FPAuthDialog.create(this).setTitle("Login to Mifos X Android Client").setMessage("Use your fingerpint to login").setCallback(
//            object: FPAuthCallback {
//                override fun onBelowAndroidMarshmallow() {
//                    Toast.makeText(this@MainActivity, "Below Marshmallow", Toast.LENGTH_LONG).show()
//                }
//
//                override fun onNoFingerprintScannerAvailable() {
//                    Toast.makeText(this@MainActivity, "No Hardware Detected", Toast.LENGTH_LONG).show()
//                }
//
//                override fun onNoFingerprintRegistered() {
//                    Toast.makeText(this@MainActivity, "Please register a fingerprint", Toast.LENGTH_LONG).show()
//                }
//
//                override fun onFingerprintAuthSuccess(message: String) {
//                    Toast.makeText(this@MainActivity, "Welcome!", Toast.LENGTH_LONG).show()
//                }
//
//                override fun onFingerprintAuthFailed(errorMessage: String) {
//                    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
//                }
//
//            }
//        ).show()
    }

    override fun onResume() {
        super.onResume()
        FPAuthDialog.create(this)
            .setTitle("Login to Mifos X Android Client")
            .setMessage("Use your fingerpint to login")
            .setCallback(
            object: FPAuthCallback {
                override fun onCancel() {
                    Toast.makeText(this@MainActivity,
                        "Cancelled", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onBelowAndroidMarshmallow() {
                    Toast.makeText(this@MainActivity,
                        "Below Marshmallow", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onNoFingerprintScannerAvailable() {
                    Toast.makeText(this@MainActivity,
                        "No Hardware Detected", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onNoFingerprintRegistered() {
                    Toast.makeText(this@MainActivity,
                        "Please register a fingerprint",
                        Toast.LENGTH_LONG).show()
                }

                override fun onFingerprintAuthSuccess(message: String) {
                    startActivity(Intent(this@MainActivity,
                        ActivitySuccess::class.java))
                }

                override fun onFingerprintAuthFailed(errorMessage: String) {
                    Toast.makeText(this@MainActivity, errorMessage,
                        Toast.LENGTH_LONG).show()
                }

            }
        ).show()
    }

}
