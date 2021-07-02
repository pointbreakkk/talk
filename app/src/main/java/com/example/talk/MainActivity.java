package com.example.talk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {


    private String version, appURL;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference db1, db2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        checkForUpdate();


//        Handler delay = new Handler();
//
//        delay.postDelayed(new Runnable() {
//            @Override
//            public void run()
//            {
//                Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(i);
//            }
//        }, 2000);
    }

    private void checkForUpdate()
    {

        try{
            version = this.getPackageManager().getPackageInfo(getPackageName(),0).versionName;

            firebaseDatabase = FirebaseDatabase.getInstance();
            db1 = firebaseDatabase.getReference("Version").child("versionNumber");
            db1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    String versionName = snapshot.getValue().toString();
                    if(!versionName.equals(version))
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle(
                                "New Version Available"
                        ).setMessage("Please update talk to the latest version for continued usage")
                                .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Version").child("URL");
                                        myref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                appURL = snapshot.getValue().toString();
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appURL)));
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }).setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .create();

                        alertDialog.setCancelable(false);
                        alertDialog.setCanceledOnTouchOutside(false);

                        alertDialog.show();


                    }

                    else{
                        int SPLASH_SCREEN_TIMEOUT = 2000;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class );
                                startActivity(intent);
                                finish();
                            }
                        }, SPLASH_SCREEN_TIMEOUT);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }
        catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }




    }
}