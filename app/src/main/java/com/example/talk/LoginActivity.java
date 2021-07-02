package com.example.talk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity
{

    EditText email, password;
    Button login, signup;
    FirebaseAuth auth;
    TextView forgotPassword;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");


        init();



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                String emailstr, passwordstr;
                emailstr = email.getText().toString();
                passwordstr = password.getText().toString();

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

                else if(!emailstr.isEmpty() && !passwordstr.isEmpty()) {
                    auth.signInWithEmailAndPassword(emailstr, passwordstr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            } else {

                                Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailstr;
                emailstr = email.getText().toString();

                if(emailstr.isEmpty())
                {
                    email.setError("Enter E-mail to reset password!");
                    email.setFocusable(true);
                }

                else if (!emailstr.isEmpty())
                {
                    final AlertDialog.Builder passwordReset = new AlertDialog.Builder(v.getContext());
                    passwordReset.setTitle("Reset Password?");

                    passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            auth.sendPasswordResetEmail(emailstr).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(LoginActivity.this, "Reset Link has been sent!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Reset Link not sent. "+ e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    });

                    passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    passwordReset.create().show();


                }


            }
        });



    }

    public void init()
    {
        email = findViewById(R.id.emailBoxlogin);
        password = findViewById(R.id.passwordBoxlogin);
        login = findViewById(R.id.loginbtnlogin);
        signup = findViewById(R.id.newaccountbtnlogin);
        forgotPassword = findViewById(R.id.forgotPassword);
        auth = FirebaseAuth.getInstance();

    }
}