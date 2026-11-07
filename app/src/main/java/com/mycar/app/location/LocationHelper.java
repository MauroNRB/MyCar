package com.mycar.app.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class LocationHelper {

    public interface LocationUpdateListener {
        void onLocationUpdated(Location location, float speedKmh, float accuracyMeters);
        void onGpsStatusChanged(boolean isActive, String message);
    }

    private final Context context;
    private final LocationUpdateListener listener;
    private FusedLocationProviderClient fusedClient;
    private LocationCallback locationCallback;
    private LocationManager nativeLocationManager;
    private LocationListener nativeListener;

    private Location lastValidLocation = null;
    private double accumulatedTripDistanceKm = 0;
    private boolean isListening = false;

    public LocationHelper(Context context, LocationUpdateListener listener) {
        this.context = context.getApplicationContext();
        this.listener = listener;
        initLocationClient();
    }

    private void initLocationClient() {
        fusedClient = LocationServices.getFusedLocationProviderClient(context);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location loc : locationResult.getLocations()) {
                    processNewLocation(loc);
                }
            }
        };

        nativeLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        nativeListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                processNewLocation(location);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {
                if (listener != null) listener.onGpsStatusChanged(true, "GPS Ativo");
            }
            @Override
            public void onProviderDisabled(String provider) {
                if (listener != null) listener.onGpsStatusChanged(false, "GPS Desativado");
            }
        };
    }

    public boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void startLocationUpdates() {
        if (!hasLocationPermission()) {
            if (listener != null) listener.onGpsStatusChanged(false, "Sem permissão de localização");
            return;
        }

        if (isListening) return;

        try {
            LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                    .setMinUpdateIntervalMillis(500)
                    .setMinUpdateDistanceMeters(0.5f)
                    .build();

            fusedClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            isListening = true;

            if (listener != null) {
                listener.onGpsStatusChanged(true, "Aguardando sinal GPS...");
            }
        } catch (SecurityException e) {
            // Fallback to native location manager
            startNativeLocationUpdates();
        }
    }

    private void startNativeLocationUpdates() {
        if (!hasLocationPermission() || nativeLocationManager == null) return;
        try {
            if (nativeLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                nativeLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, nativeListener, Looper.getMainLooper());
                isListening = true;
            }
        } catch (SecurityException ignored) {}
    }

    public void stopLocationUpdates() {
        if (!isListening) return;

        if (fusedClient != null && locationCallback != null) {
            fusedClient.removeLocationUpdates(locationCallback);
        }
        if (nativeLocationManager != null && nativeListener != null) {
            try {
                nativeLocationManager.removeUpdates(nativeListener);
            } catch (SecurityException ignored) {}
        }
        isListening = false;
    }

    private void processNewLocation(Location location) {
        if (location == null) return;

        float accuracy = location.hasAccuracy() ? location.getAccuracy() : 50f;

        // Ignore inaccurate points (> 30 meters)
        if (accuracy > 30f) {
            if (listener != null) listener.onGpsStatusChanged(true, "Sinal GPS fraco (" + Math.round(accuracy) + "m)");
            return;
        }

        // Calculate speed in km/h from m/s
        float speedKmh = 0f;
        if (location.hasSpeed()) {
            speedKmh = location.getSpeed() * 3.6f;
        } else if (lastValidLocation != null && location.getTime() > lastValidLocation.getTime()) {
            float distMeters = lastValidLocation.distanceTo(location);
            float timeSec = (location.getTime() - lastValidLocation.getTime()) / 1000f;
            if (timeSec > 0) {
                speedKmh = (distMeters / timeSec) * 3.6f;
            }
        }

        // Filter unrealistic GPS spikes (> 250 km/h)
        if (speedKmh > 250f) speedKmh = 0f;

        // Accumulate distance
        if (lastValidLocation != null) {
            float distMeters = lastValidLocation.distanceTo(location);
            if (distMeters > 2f && distMeters < 500f) { // filter noise & jumps
                accumulatedTripDistanceKm += (distMeters / 1000.0);
            }
        }
        lastValidLocation = location;

        if (listener != null) {
            listener.onGpsStatusChanged(true, "GPS Ativo (Precisão: " + String.format("%.1f", accuracy) + " m)");
            listener.onLocationUpdated(location, speedKmh, accuracy);
        }
    }

    public void resetTripDistance() {
        accumulatedTripDistanceKm = 0;
        lastValidLocation = null;
    }

    public double getAccumulatedTripDistanceKm() {
        return accumulatedTripDistanceKm;
    }
}
