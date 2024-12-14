package org.nau.diploma.chantarellecheck.fragments;


import static org.nau.diploma.chantarellecheck.activities.WarningActivity.PROPERTIES_FILE_NAME;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import rg.nau.diploma.chantarellecheck.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanTipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanTipFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScanTipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanTipFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanTipFragment newInstance(String param1, String param2) {
        ScanTipFragment fragment = new ScanTipFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.scan_tip_activity, container, false);


        MaterialButton actionButton = view.findViewById(R.id.scan_tip_action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new CameraFragment();
                replaceFragment(fragment);
            }
        });

        MaterialButton doNotRemindButton = view.findViewById(R.id.do_not_remind_scan_tip_button);
        doNotRemindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("scan_tip", false);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // Convert JsonObject to String Format
                String userString = jsonObject.toString();// Define the File Path and its Name
                File file = new File(getActivity().getFilesDir(), PROPERTIES_FILE_NAME);
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                try {
                    bufferedWriter.write(userString);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Fragment fragment = null;
                fragment = new CameraFragment();
                replaceFragment(fragment);
            }
        });

        return view;
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}