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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Hashtable;

public class RegisterationActivity extends AppCompatActivity {
    Button registerbtn, loginfromreg;

    TextInputLayout regName,regUsername, regEmail,regPass,regPhone,profession;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registeration);



        //hooks
        regName = findViewById(R.id.fullname);
        registerbtn = findViewById(R.id.register);
        loginfromreg = findViewById(R.id.loginfromreg);
        regUsername = findViewById(R.id.usernameR);
        regEmail = findViewById(R.id.email);
        regPass = findViewById(R.id.passwordR);
        regPhone = findViewById(R.id.phoneNo);
        profession = findViewById(R.id.profession);

        mAuth = FirebaseAuth.getInstance();




        loginfromreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser(this);
            }
        });



    }

    public void signuphere(RegisterationActivity view){
        String emailid = regEmail.getEditText().getText().toString();
        String pass = regPass.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(emailid, pass)
                .addOnCompleteListener(RegisterationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()) {
                                        Toast.makeText(RegisterationActivity.this, "Registered Successfully. Please verify the email address through link sent to your email.", Toast.LENGTH_SHORT).
                                        show();
                                        addData();
                                        regEmail.getEditText().setText("");
                                        regPass.getEditText().setText("");
                                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });


                        } else {
                            regEmail.getEditText().setText("");
                            regPass.getEditText().setText("");
                            Toast.makeText(getApplicationContext(),"Process Error", Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }


    private Boolean validateName(){
        String value = regName.getEditText().getText().toString();

        if(value.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateUsername(){
        String value = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(value.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }else if(value.length()>=15){
            regUsername.setError("Name too long");
            return false;
        }
        else if(!value.matches(noWhiteSpace)){
            regUsername.setError("White Spaces are not allowed and minimum 4 characters");
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail(){
        String value = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(value.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if(!value.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePass(){
        String value = regPass.getEditText().getText().toString();
        String passVal = "^" +
                "(?=.*[a-zA-Z])" +    //any letter
                "(?=.*[@#$%^&+=])" +    //any 1 special character
                "(?=\\S+$)"  +           //no white space
                ".{4,}"  +          //atleast 4 char
                "$";

        if(value.isEmpty()){
            regPass.setError("Field cannot be empty");
            return false;
        }
        else if(!value.matches(passVal)) {
            regPass.setError("Password must contain any letters ,one special character,atleast 4 character with no white spaces");
            return false;
        }
        else {
            regPass.setError(null);
            regPass.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateProfession(){
        String value = profession.getEditText().getText().toString();

        if(value.isEmpty()){
            profession.setError("Field cannot be empty");
            return false;
        }
        else {
            profession.setError(null);
            return true;
        }

    }

    private Boolean validatephone(){
        String value = regPhone.getEditText().getText().toString();

        if(value.isEmpty()){
            regPhone.setError("Field cannot be empty");
            return false;
        }
        else {
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }

    }

    public void RegisterUser(View.OnClickListener view){
        if(!validateName() | !validateUsername() | !validateEmail() | !validatePass() | !validateProfession() | !validatephone()){
            return;
        }

        signuphere(this);

       // Intent intent = new Intent(RegisterationActivity.this, PhoneOTP.class);
        //intent.putExtra("phoneNo",phnum);
        //startActivity(intent);
    }

    public void addData(){

        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String password = regPass.getEditText().getText().toString();
        String prof = profession.getEditText().getText().toString();
        String phnum = regPhone.getEditText().getText().toString();

        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");
        String uid = mAuth.getUid();

        HashMap<String,Object> users = new HashMap<>();
        users.put("name", name);
        users.put("username", username);
        users.put("email", email);
        users.put("password", password);
        users.put("profession", prof);
        users.put("phone", phnum);

        reference.child(uid).updateChildren(users);


        //DBHelper helper = new DBHelper(name,username,email,password,prof,phnum);


    }
}