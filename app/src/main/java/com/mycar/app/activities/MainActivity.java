package com.mycar.app.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mycar.app.R;
import com.mycar.app.fragments.AccelerationTestFragment;
import com.mycar.app.fragments.DashboardFragment;
import com.mycar.app.fragments.MaintenanceFragment;
import com.mycar.app.fragments.MileageFragment;
import com.mycar.app.fragments.SensorFragment;
import com.mycar.app.fragments.SettingsFragment;
import com.mycar.app.fragments.SpeedometerFragment;
import com.mycar.app.fragments.TripFragment;
import com.mycar.app.fragments.VehicleFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ActivityResultLauncher<String[]> locationPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        setupPermissionLauncher();
        checkPermissions();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_dashboard) {
                navigateToFragment(new DashboardFragment());
                return true;
            } else if (id == R.id.navigation_vehicle) {
                navigateToFragment(new VehicleFragment());
                return true;
            } else if (id == R.id.navigation_speedometer) {
                navigateToFragment(new SpeedometerFragment());
                return true;
            } else if (id == R.id.navigation_maintenance) {
                navigateToFragment(new MaintenanceFragment());
                return true;
            } else if (id == R.id.navigation_more) {
                showMoreMenu();
                return false;
            }
            return false;
        });

        if (savedInstanceState == null) {
            navigateToFragment(new DashboardFragment());
        }
    }

    private void setupPermissionLauncher() {
        locationPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    // Location permissions callback
                }
        );
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionLauncher.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    public void navigateToFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void showMoreMenu() {
        View anchor = findViewById(R.id.bottom_navigation);
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add(0, 1, 0, getString(R.string.nav_mileage));
        popup.getMenu().add(0, 2, 1, getString(R.string.nav_acceleration));
        popup.getMenu().add(0, 3, 2, getString(R.string.nav_trips));
        popup.getMenu().add(0, 4, 3, getString(R.string.nav_sensors));
        popup.getMenu().add(0, 5, 4, getString(R.string.nav_settings));

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 1:
                    navigateToFragment(new MileageFragment());
                    return true;
                case 2:
                    navigateToFragment(new AccelerationTestFragment());
                    return true;
                case 3:
                    navigateToFragment(new TripFragment());
                    return true;
                case 4:
                    navigateToFragment(new SensorFragment());
                    return true;
                case 5:
                    navigateToFragment(new SettingsFragment());
                    return true;
            }
            return false;
        });
        popup.show();
    }
}
