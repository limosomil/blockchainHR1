package com.aman.tsechackathon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aman.tsechackathon.R;
import com.aman.tsechackathon.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etContact, etPassword;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstName = etFirstName.getText().toString();
                final String lastName = etLastName.getText().toString();
                final String email = etEmail.getText().toString();
                final String contact = etContact.getText().toString();
                final String password = etPassword.getText().toString();
                if (firstName.equals("")) {
                    etFirstName.setError("Required");
                    return;
                }
                if (lastName.equals("")) {
                    etLastName.setError("Required");
                    return;
                }
                if (email.equals("")) {
                    etEmail.setError("Required");
                    return;
                }
                if (contact.equals("")) {
                    etContact.setError("Required");
                    return;
                }
                if (password.equals("")) {
                    etPassword.setError("Required");
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user=new User();
                            user.firstName=firstName;
                            user.lastName=lastName;
                            user.contact=contact;
                            user.email= email.replaceAll("[-+.^:,@]", "");
                            FirebaseUser firebaseUser=task.getResult().getUser();
                            FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).setValue(user);
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                            firebaseAuth.signInWithEmailAndPassword(email,password);
                            startActivity(DashboardActivity.makeIntent(RegisterActivity.this));
                            finishAffinity();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}
