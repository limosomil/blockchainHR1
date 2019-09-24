package com.aman.tsechackathon.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aman.tsechackathon.Constants;
import com.aman.tsechackathon.HelperClass;
import com.aman.tsechackathon.R;
import com.aman.tsechackathon.fragment.AddExperienceDialogFragment;
import com.aman.tsechackathon.model.Experience;
import com.aman.tsechackathon.model.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.aman.tsechackathon.Constants.MODE_ADD;

public class ResumeFormActivity extends AppCompatActivity {

    private EditText etCGPA, etTechnicalSkills;
    private Button btnAddExperience, btnSubmit;
    private ArrayList<Experience> experienceArrayList;
    private LinearLayout experienceLL;
    private FirebaseUser firebaseUser;
    private static final String TAG = "ResumeFormActivity";
    private User user;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_form);
        etCGPA = findViewById(R.id.etCGPA);
        etTechnicalSkills = findViewById(R.id.etSkills);
        btnAddExperience = findViewById(R.id.btnAddExperience);
        btnSubmit = findViewById(R.id.btnSubmit);
        experienceLL = findViewById(R.id.llExperience);

        experienceArrayList = new ArrayList<>();
        queue=Volley.newRequestQueue(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Log.i(TAG, "onDataChange: user: " + user);
                btnSubmit.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnAddExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("addExperience");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment dialogFragment = AddExperienceDialogFragment.newInstance(null, 0, MODE_ADD);

                dialogFragment.show(ft, "addExperience");
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.technicalSkills = etTechnicalSkills.getText().toString();
                user.CGPA = etCGPA.getText().toString();
                user.experience = experienceArrayList;
                user.tcount=experienceArrayList.size();
//                user.resume = resume;
                Log.d(TAG, "onClick: " + HelperClass.getGsonParser().toJson(user));
                FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResumeFormActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("hasUploaded").setValue(true);
                            FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                    StringRequest putRequest = new StringRequest(Request.Method.PUT, "https://api.myjson.com/bins/sffpp",
                                                                                 new Response.Listener<String>() {
                                                                                     @Override
                                                                                     public void onResponse(String response) {
                                                                                         // response
                                                                                         Log.d("Response", response);
                                                                                     }
                                                                                 },
                                                                                 new Response.ErrorListener() {
                                                                                     @Override
                                                                                     public void onErrorResponse(VolleyError error) {
                                                                                         // error
//                                                                                         Log.d("Error.Response", response);
                                                                                     }
                                                                                 }
                                    ) {

                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("key_updated", dataSnapshot.toString());


                                            return params;
                                        }

                                    };

                                    queue.add(putRequest);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Toast.makeText(ResumeFormActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ResumeFormActivity.class);
    }

    public boolean editExperience(Experience experience, int index) {
        if (isDuplicateSubjectName(experience.companyName)) {
            return false;
        } else {
            experienceArrayList.set(index, experience);
            updateUI();
            return true;
        }
    }

    public boolean addExperience(Experience experience) {
        if (isDuplicateSubjectName(experience.companyName)) {
            return false;
        } else {
            experienceArrayList.add(experience);
            updateUI();
            return true;
        }
    }

    private boolean isDuplicateSubjectName(String subjectName) {
        Set<String> set = new HashSet<String>();
        for (Experience experience : experienceArrayList) {
            set.add(experience.companyName);
        }
        set.add(subjectName);
        return set.size() <= experienceArrayList.size();
    }

    private void updateUI() {
        experienceLL.removeAllViews();
        for (int i = 0; i < experienceArrayList.size(); i++) {
            final Experience experience = experienceArrayList.get(i);
            final LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View list_item = inflater.inflate(R.layout.list_item_experience, experienceLL, false);
            list_item.setTag(i);
            list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = ResumeFormActivity.this.getSupportFragmentManager().beginTransaction();
                    Fragment prev = ResumeFormActivity.this.getSupportFragmentManager().findFragmentByTag("addExperience");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    DialogFragment dialogFragment = AddExperienceDialogFragment.newInstance(experience, Integer.parseInt(list_item.getTag().toString()), Constants.MODE_EDIT);
                    dialogFragment.show(ft, "addExperience");
                }
            });
            TextView tvCompanyName = list_item.findViewById(R.id.tvCompanyName);
            TextView tvPosition = list_item.findViewById(R.id.tvPosition);
            TextView tvDuration = list_item.findViewById(R.id.tvDuration);
            TextView tvSalary = list_item.findViewById(R.id.tvSalary);

            tvCompanyName.setText(experience.companyName);
            tvPosition.setText(experience.position);
            tvDuration.setText(experience.durationInMonths);
            tvSalary.setText(experience.salary);
            Button btnRemove = list_item.findViewById(R.id.btnRemove);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    experienceArrayList.remove(Integer.parseInt(list_item.getTag().toString()));
                    updateUI();
                }
            });
            experienceLL.addView(list_item);
        }
    }
}
