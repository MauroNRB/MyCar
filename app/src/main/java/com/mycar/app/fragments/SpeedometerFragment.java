package com.mycar.app.fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mycar.app.R;
import com.mycar.app.location.LocationHelper;
import com.mycar.app.views.SpeedometerView;

public class SpeedometerFragment extends Fragment implements LocationHelper.LocationUpdateListener {

    private SpeedometerView speedometerView;
    private TextView tvGpsStatus, tvCoordinates;
    private LocationHelper locationHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speedometer, container, false);

        speedometerView = view.findViewById(R.id.speedometer_view);
        tvGpsStatus = view.findViewById(R.id.tv_gps_status);
        tvCoordinates = view.findViewById(R.id.tv_coordinates);

        locationHelper = new LocationHelper(requireContext(), this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (locationHelper.hasLocationPermission()) {
            locationHelper.startLocationUpdates();
        } else {
            tvGpsStatus.setText(getString(R.string.settings_permission_needed));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationHelper.stopLocationUpdates();
    }

    @Override
    public void onLocationUpdated(Location location, float speedKmh, float accuracyMeters) {
        if (isAdded()) {
            speedometerView.setSpeed(speedKmh);
            String coords = String.format("Lat: %.5f | Lon: %.5f", location.getLatitude(), location.getLongitude());
            tvCoordinates.setText(coords);
        }
    }

    @Override
    public void onGpsStatusChanged(boolean isActive, String message) {
        if (isAdded()) {
            tvGpsStatus.setText(message);
            if (!isActive) {
                speedometerView.setSpeed(0);
            }
        }
    }
}
