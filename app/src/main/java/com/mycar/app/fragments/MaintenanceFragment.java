package com.mycar.app.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mycar.app.R;
import com.mycar.app.adapters.MaintenanceAdapter;
import com.mycar.app.database.DatabaseHelper;
import com.mycar.app.models.MaintenanceRecord;
import com.mycar.app.models.Vehicle;
import com.mycar.app.utils.FormatUtils;

import java.util.List;

public class MaintenanceFragment extends Fragment {

    private RecyclerView rvMaintenance;
    private Spinner spinnerFilter;
    private TextView tvEmpty;
    private MaintenanceAdapter adapter;
    private DatabaseHelper dbHelper;
    private Vehicle currentVehicle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintenance, container, false);

        rvMaintenance = view.findViewById(R.id.rv_maintenance);
        spinnerFilter = view.findViewById(R.id.spinner_category_filter);
        tvEmpty = view.findViewById(R.id.tv_empty_maintenance);

        dbHelper = new DatabaseHelper(requireContext());
        adapter = new MaintenanceAdapter();

        rvMaintenance.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvMaintenance.setAdapter(adapter);

        setupFilterSpinner();

        view.findViewById(R.id.btn_add_maintenance).setOnClickListener(v -> showAddMaintenanceDialog(null));

        adapter.setOnItemClickListener(item -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle(item.getTitle())
                    .setMessage("Custo: " + FormatUtils.formatCurrency(item.getCost()) + "\nOficina: " + (item.getWorkshop() != null ? item.getWorkshop() : "Não informada"))
                    .setPositiveButton("Editar", (dialog, which) -> showAddMaintenanceDialog(item))
                    .setNegativeButton("Excluir", (dialog, which) -> {
                        dbHelper.deleteMaintenanceRecord(item.getId());
                        loadMaintenanceRecords();
                    })
                    .setNeutralButton("Fechar", null)
                    .show();
        });

        loadMaintenanceRecords();

        return view;
    }

    private void setupFilterSpinner() {
        String[] categories = new String[]{
                getString(R.string.maintenance_filter_all),
                getString(R.string.category_oil),
                getString(R.string.category_tires),
                getString(R.string.category_brakes),
                getString(R.string.category_filters),
                getString(R.string.category_battery),
                getString(R.string.category_inspection),
                getString(R.string.category_other)
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerFilter.setAdapter(spinnerAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadMaintenanceRecords();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadMaintenanceRecords() {
        currentVehicle = dbHelper.getDefaultVehicle();
        if (currentVehicle != null) {
            String filter = spinnerFilter.getSelectedItem() != null ? spinnerFilter.getSelectedItem().toString() : null;
            List<MaintenanceRecord> list = dbHelper.getMaintenanceRecordsForVehicle(currentVehicle.getId(), filter);
            adapter.setItems(list);
            if (list.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                rvMaintenance.setVisibility(View.GONE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                rvMaintenance.setVisibility(View.VISIBLE);
            }
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            rvMaintenance.setVisibility(View.GONE);
        }
    }

    private void showAddMaintenanceDialog(MaintenanceRecord existing) {
        if (currentVehicle == null) {
            Toast.makeText(requireContext(), getString(R.string.dashboard_no_vehicle), Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_maintenance, null);

        EditText etTitle = dialogView.findViewById(R.id.et_maint_title);
        Spinner spinnerCategory = dialogView.findViewById(R.id.spinner_maint_category);
        EditText etOdometer = dialogView.findViewById(R.id.et_maint_odometer);
        EditText etCost = dialogView.findViewById(R.id.et_maint_cost);
        EditText etWorkshop = dialogView.findViewById(R.id.et_maint_workshop);
        CheckBox cbScheduled = dialogView.findViewById(R.id.cb_is_scheduled);
        EditText etTargetOdometer = dialogView.findViewById(R.id.et_maint_target_odometer);

        String[] categories = new String[]{
                getString(R.string.category_oil),
                getString(R.string.category_tires),
                getString(R.string.category_brakes),
                getString(R.string.category_filters),
                getString(R.string.category_battery),
                getString(R.string.category_inspection),
                getString(R.string.category_other)
        };
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(catAdapter);

        if (existing != null) {
            etTitle.setText(existing.getTitle());
            etOdometer.setText(String.valueOf(existing.getOdometer()));
            etCost.setText(String.valueOf(existing.getCost()));
            etWorkshop.setText(existing.getWorkshop());
            cbScheduled.setChecked(existing.isScheduled());
            etTargetOdometer.setText(String.valueOf(existing.getTargetOdometer()));
        } else {
            etOdometer.setText(String.valueOf(currentVehicle.getCurrentOdometer()));
        }

        builder.setView(dialogView);
        builder.setPositiveButton(getString(R.string.btn_save), (dialog, which) -> {
            String title = etTitle.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String odoStr = etOdometer.getText().toString().trim();
            String costStr = etCost.getText().toString().trim();
            String workshop = etWorkshop.getText().toString().trim();
            boolean isScheduled = cbScheduled.isChecked();
            String targetOdoStr = etTargetOdometer.getText().toString().trim();

            if (title.isEmpty()) return;

            double odometer = odoStr.isEmpty() ? currentVehicle.getCurrentOdometer() : Double.parseDouble(odoStr);
            double cost = costStr.isEmpty() ? 0 : Double.parseDouble(costStr);
            double targetOdo = targetOdoStr.isEmpty() ? 0 : Double.parseDouble(targetOdoStr);

            if (existing == null) {
                MaintenanceRecord record = new MaintenanceRecord(0, currentVehicle.getId(), title, category,
                        FormatUtils.getCurrentDateString(), odometer, cost, workshop, "", isScheduled, "", targetOdo);
                dbHelper.insertMaintenanceRecord(record);
            } else {
                existing.setTitle(title);
                existing.setCategory(category);
                existing.setOdometer(odometer);
                existing.setCost(cost);
                existing.setWorkshop(workshop);
                existing.setScheduled(isScheduled);
                existing.setTargetOdometer(targetOdo);
                dbHelper.updateMaintenanceRecord(existing);
            }

            loadMaintenanceRecords();
        });

        builder.setNegativeButton(getString(R.string.btn_cancel), null);
        builder.show();
    }
}
