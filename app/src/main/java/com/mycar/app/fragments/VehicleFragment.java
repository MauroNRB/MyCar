package com.mycar.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mycar.app.R;
import com.mycar.app.database.DatabaseHelper;
import com.mycar.app.models.Vehicle;

public class VehicleFragment extends Fragment {

    private EditText etName, etManufacturer, etModel, etYearFab, etYearModel, etPlate, etOdometer, etEngine;
    private Spinner spinnerFuel;
    private Button btnSave;

    private DatabaseHelper dbHelper;
    private Vehicle existingVehicle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);

        etName = view.findViewById(R.id.et_vehicle_name);
        etManufacturer = view.findViewById(R.id.et_manufacturer);
        etModel = view.findViewById(R.id.et_model);
        etYearFab = view.findViewById(R.id.et_year_fab);
        etYearModel = view.findViewById(R.id.et_year_model);
        etPlate = view.findViewById(R.id.et_plate);
        spinnerFuel = view.findViewById(R.id.spinner_fuel);
        etOdometer = view.findViewById(R.id.et_odometer);
        etEngine = view.findViewById(R.id.et_engine);
        btnSave = view.findViewById(R.id.btn_save_vehicle);

        dbHelper = new DatabaseHelper(requireContext());

        setupFuelSpinner();
        loadVehicleData();

        btnSave.setOnClickListener(v -> saveVehicle());

        return view;
    }

    private void setupFuelSpinner() {
        String[] fuels = new String[]{
                getString(R.string.fuel_flex),
                getString(R.string.fuel_gasoline),
                getString(R.string.fuel_ethanol),
                getString(R.string.fuel_diesel),
                getString(R.string.fuel_electric),
                getString(R.string.fuel_hybrid)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, fuels);
        spinnerFuel.setAdapter(adapter);
    }

    private void loadVehicleData() {
        existingVehicle = dbHelper.getDefaultVehicle();
        if (existingVehicle != null) {
            etName.setText(existingVehicle.getName());
            etManufacturer.setText(existingVehicle.getManufacturer());
            etModel.setText(existingVehicle.getModel());
            etYearFab.setText(String.valueOf(existingVehicle.getYearFab()));
            etYearModel.setText(String.valueOf(existingVehicle.getYearModel()));
            etPlate.setText(existingVehicle.getPlate());
            etOdometer.setText(String.valueOf(existingVehicle.getCurrentOdometer()));
            etEngine.setText(existingVehicle.getEngineInfo());
        }
    }

    private void saveVehicle() {
        String name = etName.getText().toString().trim();
        String manufacturer = etManufacturer.getText().toString().trim();
        String model = etModel.getText().toString().trim();
        String yearFabStr = etYearFab.getText().toString().trim();
        String yearModelStr = etYearModel.getText().toString().trim();
        String plate = etPlate.getText().toString().trim();
        String fuelType = spinnerFuel.getSelectedItem().toString();
        String odometerStr = etOdometer.getText().toString().trim();
        String engine = etEngine.getText().toString().trim();

        if (name.isEmpty() || manufacturer.isEmpty() || model.isEmpty() || odometerStr.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.required_field), Toast.LENGTH_SHORT).show();
            return;
        }

        int yearFab = yearFabStr.isEmpty() ? 2024 : Integer.parseInt(yearFabStr);
        int yearModel = yearModelStr.isEmpty() ? 2024 : Integer.parseInt(yearModelStr);
        double odometer = Double.parseDouble(odometerStr);

        if (odometer < 0) {
            Toast.makeText(requireContext(), "A quilometragem não pode ser negativa.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (existingVehicle == null) {
            Vehicle v = new Vehicle(0, name, manufacturer, model, yearFab, yearModel, plate, fuelType, odometer, engine);
            dbHelper.insertVehicle(v);
        } else {
            existingVehicle.setName(name);
            existingVehicle.setManufacturer(manufacturer);
            existingVehicle.setModel(model);
            existingVehicle.setYearFab(yearFab);
            existingVehicle.setYearModel(yearModel);
            existingVehicle.setPlate(plate);
            existingVehicle.setFuelType(fuelType);
            existingVehicle.setCurrentOdometer(odometer);
            existingVehicle.setEngineInfo(engine);
            dbHelper.updateVehicle(existingVehicle);
        }

        Toast.makeText(requireContext(), getString(R.string.vehicle_saved_success), Toast.LENGTH_SHORT).show();
    }
}
