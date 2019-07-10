package com.moksh.fingerprintauthentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.moksh.fingerprintauthenticationlibrary.FPAuthCallback;
import com.moksh.fingerprintauthenticationlibrary.FPAuthDialog;
import org.jetbrains.annotations.NotNull;

public class ActivitySuccess extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FPAuthDialog.create(this)
                .setTitle("Login to Mifos X Android Client")
                .setMessage("Use your fingerpint to login").setCallback(
                new FPAuthCallback() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onBelowAndroidMarshmallow() {

                    }

                    @Override
                    public void onNoFingerprintScannerAvailable() {

                    }

                    @Override
                    public void onNoFingerprintRegistered() {

                    }

                    @Override
                    public void onFingerprintAuthSuccess(@NotNull String message) {
                        Toast.makeText(ActivitySuccess.this, "dsafsadfsdfsdf", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFingerprintAuthFailed(@NotNull String errorMessage) {
                        Toast.makeText(ActivitySuccess.this, "failed", Toast.LENGTH_LONG).show();
                    }
                }
        ).show();

    }
}
