package com.mycar.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mycar.app.R;
import com.mycar.app.sensors.SensorHelper;
import com.mycar.app.views.AccelerationGraphView;

import java.util.Locale;

public class SensorFragment extends Fragment implements SensorHelper.SensorDataListener {

    private TextView tvStatus, tvX, tvY, tvZ, tvMagnitude;
    private AccelerationGraphView graphView;
    private SensorHelper sensorHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);

        tvStatus = view.findViewById(R.id.tv_sensor_status);
        tvX = view.findViewById(R.id.tv_axis_x);
        tvY = view.findViewById(R.id.tv_axis_y);
        tvZ = view.findViewById(R.id.tv_axis_z);
        tvMagnitude = view.findViewById(R.id.tv_magnitude);
        graphView = view.findViewById(R.id.graph_view);

        sensorHelper = new SensorHelper(requireContext(), this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorHelper.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorHelper.stopListening();
    }

    @Override
    public void onAccelerometerUpdated(float x, float y, float z, float magnitude, float filteredMovement) {
        if (isAdded()) {
            tvX.setText(String.format(Locale.getDefault(), "Eixo X: %.2f m/s²", x));
            tvY.setText(String.format(Locale.getDefault(), "Eixo Y: %.2f m/s²", y));
            tvZ.setText(String.format(Locale.getDefault(), "Eixo Z: %.2f m/s²", z));
            tvMagnitude.setText(String.format(Locale.getDefault(), "Movimento: %.2f m/s²", filteredMovement));

            graphView.addSample(filteredMovement);
        }
    }

    @Override
    public void onSensorAvailabilityChanged(boolean isAvailable) {
        if (isAdded()) {
            if (isAvailable) {
                tvStatus.setText(getString(R.string.sensor_status_available));
            } else {
                tvStatus.setText(getString(R.string.sensor_status_unavailable));
            }
        }
    }
}
