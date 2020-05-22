package com.AdrianWozniak.mymeal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.AdrianWozniak.mymeal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;

    private Button btn_login;

    private View contextView;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);


        contextView = findViewById(R.id.login_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, CalculatorActivity.class));
            finish();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        if(v==btn_login){
            if(validateCredentials(et_password.getText().toString(), et_username.getText().toString())){
                signIn(et_password.getText().toString(), et_username.getText().toString());
            }else{
                Snackbar.make( contextView, "Ups, somethink wrog!", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }
    }

    private void signIn(String password, String username) {
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, CalculatorActivity.class));
                }else{
                    Snackbar.make( contextView, task.getException().getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateCredentials(String password, String email){
        if(password.isEmpty() || email.isEmpty() ){
            return false;
        }
        if (email == null || password == null){
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(email).matches();
    }

}
