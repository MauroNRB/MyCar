package com.mycar.app.fragments;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.mycar.app.adapters.AccelerationTestAdapter;
import com.mycar.app.database.DatabaseHelper;
import com.mycar.app.location.LocationHelper;
import com.mycar.app.models.AccelerationTestRecord;
import com.mycar.app.models.Vehicle;
import com.mycar.app.utils.FormatUtils;

import java.util.List;

public class AccelerationTestFragment extends Fragment implements LocationHelper.LocationUpdateListener {

    private enum TestState {
        WAITING_GPS,
        READY,
        MEASURING,
        COMPLETED,
        INVALID
    }

    private TextView tvState, tvLiveSpeed, tvElapsedTime, tvEmpty;
    private Button btnStart, btnStop;
    private RecyclerView rvHistory;

    private AccelerationTestAdapter adapter;
    private DatabaseHelper dbHelper;
    private LocationHelper locationHelper;
    private Vehicle currentVehicle;

    private TestState currentState = TestState.WAITING_GPS;
    private long startTimeMs = 0;
    private double finalTimeSeconds = 0;
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceleration_test, container, false);

        tvState = view.findViewById(R.id.tv_accel_state);
        tvLiveSpeed = view.findViewById(R.id.tv_live_speed);
        tvElapsedTime = view.findViewById(R.id.tv_elapsed_time);
        tvEmpty = view.findViewById(R.id.tv_empty_accel);
        btnStart = view.findViewById(R.id.btn_start_accel);
        btnStop = view.findViewById(R.id.btn_stop_accel);
        rvHistory = view.findViewById(R.id.rv_accel_history);

        dbHelper = new DatabaseHelper(requireContext());
        locationHelper = new LocationHelper(requireContext(), this);
        adapter = new AccelerationTestAdapter();

        rvHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvHistory.setAdapter(adapter);

        btnStart.setOnClickListener(v -> prepareTest());
        btnStop.setOnClickListener(v -> cancelTest());

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentState == TestState.MEASURING) {
                    double elapsed = (System.currentTimeMillis() - startTimeMs) / 1000.0;
                    tvElapsedTime.setText(FormatUtils.formatTimeSeconds(elapsed));
                    timerHandler.postDelayed(this, 50);
                }
            }
        };

        updateUiForState();
        loadHistory();

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
        stopTimer();
    }

    private void prepareTest() {
        currentVehicle = dbHelper.getDefaultVehicle();
        if (currentVehicle == null) {
            Toast.makeText(requireContext(), getString(R.string.dashboard_no_vehicle), Toast.LENGTH_SHORT).show();
            return;
        }

        currentState = TestState.READY;
        tvElapsedTime.setText("0,00 s");
        updateUiForState();
    }

    private void cancelTest() {
        stopTimer();
        currentState = TestState.INVALID;
        updateUiForState();
    }

    private void updateUiForState() {
        switch (currentState) {
            case WAITING_GPS:
                tvState.setText(getString(R.string.acceleration_state_waiting_gps));
                btnStart.setEnabled(false);
                btnStop.setEnabled(false);
                break;
            case READY:
                tvState.setText(getString(R.string.acceleration_state_ready));
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                break;
            case MEASURING:
                tvState.setText(getString(R.string.acceleration_state_measuring));
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                break;
            case COMPLETED:
                tvState.setText(getString(R.string.acceleration_state_completed));
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                break;
            case INVALID:
                tvState.setText(getString(R.string.acceleration_state_invalid));
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                break;
        }
    }

    private void startTimer() {
        startTimeMs = System.currentTimeMillis();
        timerHandler.post(timerRunnable);
    }

    private void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    public void onLocationUpdated(Location location, float speedKmh, float accuracyMeters) {
        if (!isAdded()) return;

        tvLiveSpeed.setText(Math.round(speedKmh) + " km/h");

        if (currentState == TestState.WAITING_GPS && accuracyMeters <= 20f) {
            currentState = TestState.READY;
            updateUiForState();
        }

        // Logic to trigger start timing when stationary vehicle starts moving (> 2 km/h)
        if (currentState == TestState.READY && speedKmh > 2f) {
            currentState = TestState.MEASURING;
            updateUiForState();
            startTimer();
        }

        // Logic to complete test when speed reaches 100 km/h
        if (currentState == TestState.MEASURING && speedKmh >= 100f) {
            stopTimer();
            finalTimeSeconds = (System.currentTimeMillis() - startTimeMs) / 1000.0;
            currentState = TestState.COMPLETED;
            updateUiForState();
            tvElapsedTime.setText(FormatUtils.formatTimeSeconds(finalTimeSeconds));

            // Save result to database
            if (currentVehicle != null) {
                AccelerationTestRecord record = new AccelerationTestRecord(0, currentVehicle.getId(), finalTimeSeconds,
                        FormatUtils.getCurrentDateTimeString(), 0, 100);
                dbHelper.insertAccelerationTestRecord(record);
                loadHistory();
            }
        }
    }

    @Override
    public void onGpsStatusChanged(boolean isActive, String message) {
        if (!isActive && isAdded()) {
            if (currentState == TestState.MEASURING) {
                cancelTest();
            } else {
                currentState = TestState.WAITING_GPS;
                updateUiForState();
            }
        }
    }

    private void loadHistory() {
        currentVehicle = dbHelper.getDefaultVehicle();
        if (currentVehicle != null) {
            List<AccelerationTestRecord> history = dbHelper.getAccelerationTestsForVehicle(currentVehicle.getId());
            adapter.setItems(history);
            if (history.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                rvHistory.setVisibility(View.VISIBLE);
            }
        }
    }
}
