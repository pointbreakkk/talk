package com.example.talk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity
{
    EditText email, password, name;
    Button login, signup;
    FirebaseAuth auth;
    public FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");


        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.show();
                String emailstr, passwordstr, namestr;
                emailstr = email.getText().toString();
                passwordstr = password.getText().toString();
                namestr = name.getText().toString();

                if(emailstr.isEmpty())
                {
                    dialog.dismiss();
                    email.setError("E-mail is empty!");
                    email.setFocusable(true);
                }
                if(passwordstr.isEmpty())
                {
                    dialog.dismiss();
                    password.setError("Password is empty!");
                    password.setFocusable(true);
                }
                if(namestr.isEmpty())
                {
                    dialog.dismiss();
                    name.setError("Name is empty!");
                    name.setFocusable(true);
                }

                else if(!emailstr.isEmpty() && !passwordstr.isEmpty() && !namestr.isEmpty()) {

                    User user = new User();
                    user.setEmail(emailstr);
                    user.setName(namestr);
                    user.setPassword(passwordstr);

                    auth.createUserWithEmailAndPassword(emailstr, passwordstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();

                                database.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SignUpActivity.this, "New Account Created!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    }
                                });



                            }
                            else {
                                Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Unknown Error Occured", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void init()
    {
        name = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.emailBoxsignup);
        password = findViewById(R.id.passwordBoxsignup);
        login = findViewById(R.id.loginbtnsignup);
        signup = findViewById(R.id.createnewaccbtnsignup);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

    }
}