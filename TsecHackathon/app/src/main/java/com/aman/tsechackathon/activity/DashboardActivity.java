package com.aman.tsechackathon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aman.tsechackathon.MainActivity;
import com.aman.tsechackathon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnLogOut, btnAddResume;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnAddResume = findViewById(R.id.btnAddResume);
        firebaseAuth = FirebaseAuth.getInstance();

        btnAddResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ResumeFormActivity.makeIntent(DashboardActivity.this));
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(MainActivity.makeIntent(DashboardActivity.this));
                finishAffinity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("hasUploaded").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Boolean.class) != null && dataSnapshot.getValue(Boolean.class)) {
                    btnAddResume.setEnabled(false);
                    btnAddResume.setText("Already Uploaded Resume");
                } else {
                    btnAddResume.setEnabled(true);
                    btnAddResume.setText("Add Resume");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, DashboardActivity.class);
    }
}
