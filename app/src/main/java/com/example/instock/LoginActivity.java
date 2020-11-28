package com.example.instock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button gotoregbtn , login;
    TextInputLayout logEmail,logPass;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";
    private int RC_SIGN_IN = 1;

    FirebaseDatabase root;
    DatabaseReference ref;



    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //hooks
        signInButton = findViewById(R.id.google_signin);
        gotoregbtn = findViewById(R.id.gotoreg);
        logEmail = findViewById(R.id.email);
        logPass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        //create request
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        //signin button listener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlesignIn();
            }
        });



//        if(mAuth.getCurrentUser() != null &&  mAuth.getCurrentUser().isEmailVerified()){
//            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

    }

    private void googlesignIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount acc = task.getResult(ApiException.class);
                //Toast.makeText(getApplicationContext(),"Signed In Successfully", Toast.LENGTH_LONG).show();
                FirebaseGoogleAuth(acc);

            }
            catch (ApiException e){
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                //FirebaseGoogleAuth(null);
            }
        }
    }



    private void FirebaseGoogleAuth(GoogleSignInAccount acc) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    addData();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Sorry authentication failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addData(){
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            String name = signInAccount.getDisplayName();
            String emailid = signInAccount.getEmail();

            root = FirebaseDatabase.getInstance();
            ref = root.getReference("users");
            String uid = mAuth.getUid();

            HashMap<String, Object> users = new HashMap<>();
            users.put("name", name);
            users.put("email", emailid);

            ref.child(uid).updateChildren(users);

//            HashMap<String, Object> categories = new HashMap<>();
//            categories.put("Groceries", new Object("apple","1"
//            ));
//            categories.put("Grooming");
//            categories.put("Tech");
//            categories.put("Stationary");
//            categories.put("Others");
        }
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