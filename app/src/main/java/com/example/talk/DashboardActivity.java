package com.example.talk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

   BottomNavigationView bnav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().hide();

        init();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();

        bnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment selected = null;

                switch (item.getItemId())
                {
                    case R.id.home:
                        selected = new HomeFragment();
                        break;
                    case R.id.about:
                        selected = new AboutFragment();
                        break;
                    case R.id.logout:
                        selected = new LogOutFragment();
                        break;
                    case R.id.history:
                        selected = new HistoryFragment();
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selected).commit();
                return true;
            }
        });


    }

    private void init()
    {
        bnav = findViewById(R.id.bottomNavigationView);
    }


}