package com.example.talk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LogOutFragment extends Fragment {

    FirebaseAuth firebaseAuth;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), LoginActivity.class));

    }
}
