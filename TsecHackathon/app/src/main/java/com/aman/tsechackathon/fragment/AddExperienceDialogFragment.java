package com.aman.tsechackathon.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aman.tsechackathon.Constants;
import com.aman.tsechackathon.HelperClass;
import com.aman.tsechackathon.R;
import com.aman.tsechackathon.activity.ResumeFormActivity;
import com.aman.tsechackathon.model.Experience;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * User: Aman
 * Date: 21-09-2019
 * Time: 05:09 AM
 */
public class AddExperienceDialogFragment extends DialogFragment {
    private static final String TAG = "AddExperienceDialogFrag";
    private static final String MODE_KEY = "mode";
    private static final String EXPERIENCE_KEY = "experience";
    private static final String INDEX_KEY = "index";
    private EditText etCompanyName, etPosition, etDuration, etSalary;
    private Button btnDone;

    public static AddExperienceDialogFragment newInstance(Experience experience, int index, String mode) {
        if (mode.equals(Constants.MODE_ADD)) {
            Bundle args = new Bundle();
            args.putString(MODE_KEY, mode);
            AddExperienceDialogFragment fragment = new AddExperienceDialogFragment();
            fragment.setArguments(args);
            return fragment;
        } else if (mode.equals(Constants.MODE_EDIT)) {
            Bundle args = new Bundle();
            String subjectJson = HelperClass.getGsonParser().toJson(experience);
            args.putString(EXPERIENCE_KEY, subjectJson);
            args.putInt(INDEX_KEY, index);
            args.putString(MODE_KEY, mode);
            AddExperienceDialogFragment fragment = new AddExperienceDialogFragment();
            fragment.setArguments(args);
            return fragment;
        }
        return new AddExperienceDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_experience, container, false);
        etCompanyName = view.findViewById(R.id.etCompanyName);
        etPosition = view.findViewById(R.id.etPosition);
        etDuration = view.findViewById(R.id.etDuration);
        etSalary = view.findViewById(R.id.etSalary);
        btnDone = view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() != null) {
                    if (getArguments().getString(MODE_KEY, Constants.MODE_ADD).equals(Constants.MODE_EDIT)) {
                        if (TextUtils.isEmpty(etCompanyName.getText())) {
                            etCompanyName.setError("Name cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(etPosition.getText())) {
                            etPosition.setError("Position cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(etDuration.getText())) {
                            etCompanyName.setError("Duration cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(etSalary.getText())) {
                            etCompanyName.setError("Salary cannot be empty");
                            return;
                        } else {
                            Experience experience = new Experience();
                            experience.companyName = etCompanyName.getText().toString();
                            experience.durationInMonths = etDuration.getText().toString();
                            experience.salary = etSalary.getText().toString();
                            experience.position = etPosition.getText().toString();
                            if (getActivity() instanceof ResumeFormActivity) {
                                ((ResumeFormActivity) getActivity()).editExperience(experience, getArguments().getInt(INDEX_KEY));
                            }
                            AddExperienceDialogFragment.this.dismiss();
                        }
                    } else if (getArguments().getString(MODE_KEY, Constants.MODE_ADD).equals(Constants.MODE_ADD)) {
                        if (TextUtils.isEmpty(etCompanyName.getText())) {
                            etCompanyName.setError("Name cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(etPosition.getText())) {
                            etPosition.setError("Position cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(etDuration.getText())) {
                            etCompanyName.setError("Duration cannot be empty");
                            return;
                        } else if (TextUtils.isEmpty(etSalary.getText())) {
                            etCompanyName.setError("Salary cannot be empty");
                            return;
                        } else {
                            Experience experience = new Experience();
                            experience.companyName = etCompanyName.getText().toString();
                            experience.durationInMonths = etDuration.getText().toString();
                            experience.salary = etSalary.getText().toString();
                            experience.position = etPosition.getText().toString();
                            if (getActivity() instanceof ResumeFormActivity) {
                                ((ResumeFormActivity) getActivity()).addExperience(experience);
                            }
                            AddExperienceDialogFragment.this.dismiss();
                        }
                    }
                }
            }
        });
        if (getArguments() != null) {
            String experienceJson = (getArguments().getString(EXPERIENCE_KEY, "null"));
            if (!experienceJson.equals("null")) {
                Log.i(TAG, "onCreateView: subject json " + experienceJson);
                Experience experience = HelperClass.getGsonParser().fromJson(experienceJson, Experience.class);
                etCompanyName.setText(experience.companyName);
                etDuration.setText(experience.durationInMonths);
                etSalary.setText(experience.salary);
                etPosition.setText(experience.position);

            }
        }
        return view;
    }
}
