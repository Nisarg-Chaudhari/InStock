package com.example.instock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button gotoregbtn , login;
    TextInputLayout logEmail,logPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //hooks
        gotoregbtn = findViewById(R.id.gotoreg);
        logEmail = findViewById(R.id.email);
        logPass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();

//        if(mAuth.getCurrentUser() != null){
//            Intent intent = new Intent(getApplicationContext(),test.class);
//            startActivity(intent);
//            finish();
        //}

    }

    public void goToRegister(View view) {
        Intent intent = new Intent(getApplicationContext(),RegisterationActivity.class);
        startActivity(intent);
    }



    public void signinhere(View view) {


        String email = logEmail.getEditText().getText().toString();
        String pass = logPass.getEditText().getText().toString();

        if(email.isEmpty()){
            return;
        }

        if(pass.isEmpty()){
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if(mAuth.getCurrentUser().isEmailVerified()){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                //intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                                //intent.putExtra("uid",mAuth.getCurrentUser().getUid());
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Please verify your email address", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            logEmail.getEditText().setText("");
                            logPass.getEditText().setText("");
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });



    }


}