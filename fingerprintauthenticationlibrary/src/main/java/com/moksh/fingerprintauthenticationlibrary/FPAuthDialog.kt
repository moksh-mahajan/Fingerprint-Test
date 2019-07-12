package com.moksh.fingerprintauthenticationlibrary

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class FPAuthDialog private constructor(context: Context) : FingerprintNotSupportedCallback {
    override fun onSuccess() {
        dialog!!.cancel()
    }

    override fun onBelowAndroidMarshmallow() {
        dialog!!.cancel()
    }

    override fun onNoFingerprintScannerAvailable() {
        dialog!!.cancel()
    }

    override fun onNoFingerprintRegistered() {
        dialog!!.cancel()
    }

    private lateinit var context: Context
    private lateinit var title: String
    private lateinit var message: String
    private lateinit var builder: AlertDialog.Builder
    private var dialog: AlertDialog? = null
    private lateinit var dialogView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var tvDialogTitle: TextView
    private lateinit var tvDialogMessage: TextView
    private lateinit var btnCancel: AppCompatButton
    private lateinit var fpAuthHelper: FPAuthHelper
    private lateinit var mFPAuthCallback: FPAuthCallback


    init {
        this.context = context
        title = ""
        message = ""
        inflater = LayoutInflater.from(context)
        builder = AlertDialog.Builder(context)

        dialogView = inflater.inflate(R.layout.fingerprint_auth_dialog, null)
        tvDialogTitle = dialogView.findViewById(R.id.fingerprint_dialog_title)
        btnCancel = dialogView.findViewById(R.id.fingerprint_dialog_cancel)
        tvDialogMessage = dialogView.findViewById(R.id.fingerprint_dialog_message)
    }

    companion object {
        @JvmStatic
        fun create(context: Context): FPAuthDialog {
            return FPAuthDialog(context)
        }
    }

    fun setCallback(fpAuthCallback: FPAuthCallback): FPAuthDialog {
        mFPAuthCallback = fpAuthCallback
        fpAuthHelper = FPAuthHelper(context, mFPAuthCallback, this, dialogView)
        return this
    }

    fun setTitle(title: String): FPAuthDialog {
        this.title = title
        return this
    }

    fun setTitle(resTitle: Int): FPAuthDialog {
        title = context.resources.getString(resTitle)
        return this
    }

    fun setMessage(message: String): FPAuthDialog {
        this.message = message
        return this
    }

    fun dismiss() {
        fpAuthHelper.stopAuth()
        if (dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    fun setMesage(resMessage: Int): FPAuthDialog {
        message = context.resources.getString(resMessage)
        return this
    }

    fun show() {
        tvDialogTitle.text = title
        tvDialogMessage.text = message
        btnCancel.setOnClickListener {
            fpAuthHelper.stopAuth()
            mFPAuthCallback.onCancel()
            dialog!!.cancel()
        }
        builder.setView(dialogView)
        dialog = builder.create()
        dialog!!.setCancelable(false)
        dialog!!.show()

        fpAuthHelper.startAuth()
    }
}