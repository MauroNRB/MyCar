package com.mycar.app.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mycar.app.R;

public class SettingsFragment extends Fragment {

    private Button btnRequestLocation;
    private ActivityResultLauncher<String> locationPermissionLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        btnRequestLocation = view.findViewById(R.id.btn_request_location);

        locationPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Toast.makeText(requireContext(), getString(R.string.settings_permission_granted), Toast.LENGTH_SHORT).show();
                        updatePermissionButton();
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.settings_permission_needed), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnRequestLocation.setOnClickListener(v -> {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        });

        updatePermissionButton();

        return view;
    }

    private void updatePermissionButton() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            btnRequestLocation.setText(getString(R.string.settings_permission_granted));
            btnRequestLocation.setEnabled(false);
        } else {
            btnRequestLocation.setText(getString(R.string.settings_permission_location));
            btnRequestLocation.setEnabled(true);
        }
    }
}
