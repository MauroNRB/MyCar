package com.mycar.app.fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mycar.app.R;
import com.mycar.app.adapters.TripAdapter;
import com.mycar.app.database.DatabaseHelper;
import com.mycar.app.location.LocationHelper;
import com.mycar.app.models.TripRecord;
import com.mycar.app.models.Vehicle;
import com.mycar.app.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

public class TripFragment extends Fragment implements LocationHelper.LocationUpdateListener {

    private TextView tvLiveDistance, tvEmpty;
    private Button btnStart, btnStop;
    private RecyclerView rvTrips;

    private TripAdapter adapter;
    private DatabaseHelper dbHelper;
    private LocationHelper locationHelper;
    private Vehicle currentVehicle;

    private boolean isTripActive = false;
    private String tripStartTime = "";
    private List<Float> tripSpeeds = new ArrayList<>();
    private float maxSpeed = 0f;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        tvLiveDistance = view.findViewById(R.id.tv_trip_live_distance);
        tvEmpty = view.findViewById(R.id.tv_empty_trips);
        btnStart = view.findViewById(R.id.btn_start_trip);
        btnStop = view.findViewById(R.id.btn_stop_trip);
        rvTrips = view.findViewById(R.id.rv_trips);

        dbHelper = new DatabaseHelper(requireContext());
        locationHelper = new LocationHelper(requireContext(), this);
        adapter = new TripAdapter();

        rvTrips.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvTrips.setAdapter(adapter);

        btnStart.setOnClickListener(v -> startTrip());
        btnStop.setOnClickListener(v -> stopTrip());

        updateButtons();
        loadTripHistory();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (locationHelper.hasLocationPermission()) {
            locationHelper.startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationHelper.stopLocationUpdates();
    }

    private void startTrip() {
        currentVehicle = dbHelper.getDefaultVehicle();
        if (currentVehicle == null) {
            Toast.makeText(requireContext(), getString(R.string.dashboard_no_vehicle), Toast.LENGTH_SHORT).show();
            return;
        }

        isTripActive = true;
        tripStartTime = FormatUtils.getCurrentDateTimeString();
        locationHelper.resetTripDistance();
        tripSpeeds.clear();
        maxSpeed = 0f;

        updateButtons();
        Toast.makeText(requireContext(), getString(R.string.trip_in_progress), Toast.LENGTH_SHORT).show();
    }

    private void stopTrip() {
        if (!isTripActive) return;

        isTripActive = false;
        double distKm = locationHelper.getAccumulatedTripDistanceKm();
        String endTime = FormatUtils.getCurrentDateTimeString();

        float avgSpeed = 0f;
        if (!tripSpeeds.isEmpty()) {
            float sum = 0;
            for (float s : tripSpeeds) sum += s;
            avgSpeed = sum / tripSpeeds.size();
        }

        if (currentVehicle != null && distKm > 0.05) {
            TripRecord trip = new TripRecord(0, currentVehicle.getId(), tripStartTime, endTime, distKm, avgSpeed, maxSpeed);
            dbHelper.insertTripRecord(trip);
            Toast.makeText(requireContext(), getString(R.string.trip_saved), Toast.LENGTH_SHORT).show();
        }

        updateButtons();
        loadTripHistory();
    }

    private void updateButtons() {
        btnStart.setEnabled(!isTripActive);
        btnStop.setEnabled(isTripActive);
    }

    private void loadTripHistory() {
        currentVehicle = dbHelper.getDefaultVehicle();
        if (currentVehicle != null) {
            List<TripRecord> list = dbHelper.getTripsForVehicle(currentVehicle.getId());
            adapter.setItems(list);
            if (list.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                rvTrips.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                rvTrips.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLocationUpdated(Location location, float speedKmh, float accuracyMeters) {
        if (!isAdded()) return;

        if (isTripActive) {
            tvLiveDistance.setText(FormatUtils.formatDistance(locationHelper.getAccumulatedTripDistanceKm()));
            if (speedKmh > 1f) {
                tripSpeeds.add(speedKmh);
                if (speedKmh > maxSpeed) maxSpeed = speedKmh;
            }
        }
    }

    @Override
    public void onGpsStatusChanged(boolean isActive, String message) {
    }
}
