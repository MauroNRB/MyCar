package com.mycar.app.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mycar.app.R;
import com.mycar.app.adapters.MileageAdapter;
import com.mycar.app.database.DatabaseHelper;
import com.mycar.app.models.MileageRecord;
import com.mycar.app.models.Vehicle;
import com.mycar.app.utils.FormatUtils;

import java.util.List;

public class MileageFragment extends Fragment {

    private RecyclerView rvMileage;
    private TextView tvEmpty;
    private MileageAdapter adapter;
    private DatabaseHelper dbHelper;
    private Vehicle currentVehicle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mileage, container, false);

        rvMileage = view.findViewById(R.id.rv_mileage);
        tvEmpty = view.findViewById(R.id.tv_empty_mileage);

        dbHelper = new DatabaseHelper(requireContext());
        adapter = new MileageAdapter();

        rvMileage.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvMileage.setAdapter(adapter);

        view.findViewById(R.id.btn_add_mileage).setOnClickListener(v -> showAddMileageDialog());

        loadRecords();

        return view;
    }

    private void loadRecords() {
        currentVehicle = dbHelper.getDefaultVehicle();
        if (currentVehicle != null) {
            List<MileageRecord> records = dbHelper.getMileageRecordsForVehicle(currentVehicle.getId());
            adapter.setItems(records);
            if (records.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                rvMileage.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                rvMileage.setVisibility(View.VISIBLE);
            }
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            rvMileage.setVisibility(View.GONE);
        }
    }

    private void showAddMileageDialog() {
        if (currentVehicle == null) {
            Toast.makeText(requireContext(), getString(R.string.dashboard_no_vehicle), Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_mileage, null);
        EditText etMileage = dialogView.findViewById(R.id.et_dialog_mileage);
        EditText etNotes = dialogView.findViewById(R.id.et_dialog_notes);

        builder.setView(dialogView);
        builder.setPositiveButton(getString(R.string.btn_save), (dialog, which) -> {
            String valStr = etMileage.getText().toString().trim();
            String notes = etNotes.getText().toString().trim();

            if (valStr.isEmpty()) return;

            double newOdometer = Double.parseDouble(valStr);

            if (newOdometer < currentVehicle.getCurrentOdometer()) {
                String msg = String.format(getString(R.string.mileage_error_lower), String.valueOf(currentVehicle.getCurrentOdometer()));
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
                return;
            }

            MileageRecord record = new MileageRecord(0, currentVehicle.getId(), newOdometer, FormatUtils.getCurrentDateTimeString(), notes, 0);
            dbHelper.insertMileageRecord(record);
            loadRecords();
        });

        builder.setNegativeButton(getString(R.string.btn_cancel), null);
        builder.show();
    }
}
