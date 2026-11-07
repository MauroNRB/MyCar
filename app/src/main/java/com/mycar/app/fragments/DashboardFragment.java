package com.mycar.app.fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mycar.app.R;
import com.mycar.app.activities.MainActivity;
import com.mycar.app.database.DatabaseHelper;
import com.mycar.app.location.LocationHelper;
import com.mycar.app.models.Vehicle;
import com.mycar.app.utils.FormatUtils;

public class DashboardFragment extends Fragment implements LocationHelper.LocationUpdateListener {

    private TextView tvVehicleTitle, tvVehicleSubtitle, tvDashOdometer, tvDashSpeed, tvDashDistanceToday, tvDashPendingMaint;
    private Button btnRegisterVehicle;

    private DatabaseHelper dbHelper;
    private LocationHelper locationHelper;
    private Vehicle currentVehicle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvVehicleTitle = view.findViewById(R.id.tv_vehicle_title);
        tvVehicleSubtitle = view.findViewById(R.id.tv_vehicle_subtitle);
        tvDashOdometer = view.findViewById(R.id.tv_dash_odometer);
        tvDashSpeed = view.findViewById(R.id.tv_dash_speed);
        tvDashDistanceToday = view.findViewById(R.id.tv_dash_distance_today);
        tvDashPendingMaint = view.findViewById(R.id.tv_dash_pending_maint);
        btnRegisterVehicle = view.findViewById(R.id.btn_register_vehicle);

        dbHelper = new DatabaseHelper(requireContext());
        locationHelper = new LocationHelper(requireContext(), this);

        btnRegisterVehicle.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new VehicleFragment());
            }
        });

        view.findViewById(R.id.btn_action_mileage).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new MileageFragment());
            }
        });

        view.findViewById(R.id.btn_action_speedometer).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new SpeedometerFragment());
            }
        });

        view.findViewById(R.id.btn_action_acceleration).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new AccelerationTestFragment());
            }
        });

        view.findViewById(R.id.btn_action_sensors).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(new SensorFragment());
            }
        });

        loadDashboardData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDashboardData();
        if (locationHelper.hasLocationPermission()) {
            locationHelper.startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationHelper.stopLocationUpdates();
    }

    private void loadDashboardData() {
        currentVehicle = dbHelper.getDefaultVehicle();
        if (currentVehicle != null) {
            tvVehicleTitle.setText(currentVehicle.getName() + " (" + currentVehicle.getModel() + ")");
            tvVehicleSubtitle.setText(currentVehicle.getManufacturer() + " • " + currentVehicle.getFuelType());
            tvDashOdometer.setText(FormatUtils.formatOdometer(currentVehicle.getCurrentOdometer()));
            btnRegisterVehicle.setText(getString(R.string.btn_edit));

            double distToday = dbHelper.getDistanceToday(currentVehicle.getId());
            tvDashDistanceToday.setText(FormatUtils.formatDistance(distToday));

            int pendingMaint = dbHelper.getPendingMaintenancesCount(currentVehicle.getId(), currentVehicle.getCurrentOdometer());
            tvDashPendingMaint.setText(String.valueOf(pendingMaint));
        } else {
            tvVehicleTitle.setText(getString(R.string.dashboard_no_vehicle));
            tvVehicleSubtitle.setText(getString(R.string.dashboard_no_vehicle_msg));
            tvDashOdometer.setText("0 km");
            tvDashDistanceToday.setText("0,0 km");
            tvDashPendingMaint.setText("0");
            btnRegisterVehicle.setText(getString(R.string.dashboard_register_vehicle));
        }
    }

    @Override
    public void onLocationUpdated(Location location, float speedKmh, float accuracyMeters) {
        if (isAdded()) {
            tvDashSpeed.setText(Math.round(speedKmh) + " km/h");
        }
    }

    @Override
    public void onGpsStatusChanged(boolean isActive, String message) {
        if (!isActive && isAdded()) {
            tvDashSpeed.setText("0 km/h");
        }
    }
}
