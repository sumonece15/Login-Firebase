package com.example.loginfirebase;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.loginfirebase.R.id.signInButtonId;
import static com.example.loginfirebase.R.id.signUpTextViewId;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signInEmailEditText, signInPasswordEditText;
    private Button signInButton;
    private TextView signUpTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In Activity");

        mAuth = FirebaseAuth.getInstance();


        signInEmailEditText = (EditText) findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditText = (EditText) findViewById(R.id.signInPasswordEditTextId);
        progressBar = (ProgressBar) findViewById(R.id.progressBarId);

        signInButton = (Button) findViewById(R.id.signInButtonId);
        signUpTextView = (TextView) findViewById(R.id.signUpTextViewId);

        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signInButtonId:

                userLogin();

                break;

            case R.id.signUpTextViewId:

                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);

                break;
        }

    }

    private void userLogin() {

        String email = signInEmailEditText.getText().toString();
        String password = signInPasswordEditText.getText().toString();

        //checking the validity of the Email

        if (email.isEmpty()) {
            signInEmailEditText.setError("Enter an Email Address");
            signInEmailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signInEmailEditText.setError("Enter a valid Email Address");
            signInEmailEditText.requestFocus();
            return;
        }

        //checking the validity of the password

        if (password.isEmpty()) {
            signInPasswordEditText.setError("Enter a password");
            signInPasswordEditText.requestFocus();
            return;
        }

        if (password.length() > 6) {
            signInPasswordEditText.setError("Minimum length of a password should be 6");
            signInPasswordEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {

                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                    finish();

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                }

                else {

                    Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}