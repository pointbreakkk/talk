package com.example.talk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class HomeFragment extends Fragment {

    EditText code;
    Button join, share;
    View view;
    FirebaseFirestore db;
    SignUpActivity signUpActivity = new SignUpActivity();
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;




    }

    @Override
    public void onStart() {
        super.onStart();
        init();

        URL serverurl;

        try {
            serverurl = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultoption =
                    new JitsiMeetConferenceOptions.Builder().setServerURL(serverurl).setWelcomePageEnabled(false).build();


            JitsiMeet.setDefaultConferenceOptions(defaultoption);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codestr = code.getText().toString();
                if(codestr.isEmpty())
                {
                    code.setError("Enter Code");
                }
                else if (!codestr.isEmpty())
                {
                    JitsiMeetConferenceOptions options =
                            new JitsiMeetConferenceOptions.Builder().setRoom(codestr).setWelcomePageEnabled(false).build();

                    JitsiMeetActivity.launch(getContext(), options);
                }



            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codestr = code.getText().toString();
                if(codestr.isEmpty())
                {
                    code.setError("Enter Code");
                }
                else if(!codestr.isEmpty())
                {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");

//                    db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            String name = documentSnapshot.getString("name");
//                            String body = "Open your talk app and use this link to join a video chat with " + name;
//                            i.putExtra(Intent.EXTRA_TEXT, body);
//                            startActivity(Intent.createChooser(i, "Share Using:"));
//                        }
//                    });

                    String body = "I am inviting you to join me in a video call using the code: " + codestr;
                    i.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(i, "Share Using"));

                }
            }
        });
    }

    public void init()
    {
        code = view.findViewById(R.id.meetingcode);
        join = view.findViewById(R.id.joinbtn);
        share = view.findViewById(R.id.sharebtn);
    }
}
