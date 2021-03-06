package com.example.auto_learn_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class SignupActivity extends AppCompatActivity {
    Button btnSignUp;
    EditText email, passwod,passwod2,name;
    FirebaseAuth mfirebaseAuth;
    FirebaseUser user;
    FirebaseAnalytics mAnalytics;
    androidx.appcompat.widget.Toolbar toolbar;
    private String name2 = new String("");


    // Defining Permission codes.
    // We can give any value
    // but unique for each permission.
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar = findViewById(R.id.toolbarSignup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign up form");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mfirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailReset);
        passwod = findViewById(R.id.passwordSignup);
        passwod2 = findViewById(R.id.confirmSignup);
        btnSignUp = findViewById(R.id.next_mav_button);
        name = findViewById(R.id.nameSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email2 = email.getText().toString();
                String pass = passwod.getText().toString();
                String pass2 = passwod2.getText().toString();
                name2 += name.getText().toString();
                if (email2.isEmpty()) {
                    email.setError("Enter a email");
                    email.requestFocus();
                } else if (pass.isEmpty()) {
                    passwod.setError("Enter a password");
                    passwod.requestFocus();
                } else if (pass2.isEmpty()) {
                    passwod2.setError("Enter a password");
                    passwod2.requestFocus();
                } else if(name2.isEmpty()){
                    name.setError("Enter a password");
                    name.requestFocus();
                } else {

                    if (pass.equals(pass2))
                    {
                        mfirebaseAuth.createUserWithEmailAndPassword(email2, pass).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                                } else {
                                    user = mfirebaseAuth.getCurrentUser();
                                    if (user != null) {

                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name2).build();
                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Intent s = new Intent(SignupActivity.this, ProfileIDSignup.class);
                                                            startActivity(s);
                                                        }
                                                    }
                                                });

                                    }
                                }
                            }
                        });
                    }
                    else
                    {
                        passwod2.setError("Password must match");
                        passwod2.requestFocus();
                    }

                }

            }
        });
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // handle arrow click here
            if (item.getItemId() == android.R.id.home) {
                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish(); // close this activity and return to preview activity (if there is any)
            }

            return super.onOptionsItemSelected(item);
    }


}