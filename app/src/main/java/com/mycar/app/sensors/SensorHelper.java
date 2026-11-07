package com.mycar.app.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorHelper implements SensorEventListener {

    public interface SensorDataListener {
        void onAccelerometerUpdated(float x, float y, float z, float magnitude, float filteredMovement);
        void onSensorAvailabilityChanged(boolean isAvailable);
    }

    private final SensorManager sensorManager;
    private Sensor accelerometer;
    private final SensorDataListener listener;
    private boolean isRegistered = false;

    private final float[] gravity = new float[3];
    private final float[] linearAcceleration = new float[3];

    public SensorHelper(Context context, SensorDataListener listener) {
        this.listener = listener;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    public boolean isAccelerometerAvailable() {
        return accelerometer != null;
    }

    public void startListening() {
        if (accelerometer != null && !isRegistered && sensorManager != null) {
            isRegistered = sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            if (listener != null) {
                listener.onSensorAvailabilityChanged(isRegistered);
            }
        } else if (listener != null) {
            listener.onSensorAvailabilityChanged(false);
        }
    }

    public void stopListening() {
        if (isRegistered && sensorManager != null) {
            sensorManager.unregisterListener(this);
            isRegistered = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calculate raw magnitude
            float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

            // High-pass filter to isolate linear acceleration (subtract gravity approximation ~9.81 m/s²)
            final float alpha = 0.8f;
            gravity[0] = alpha * gravity[0] + (1 - alpha) * x;
            gravity[1] = alpha * gravity[1] + (1 - alpha) * y;
            gravity[2] = alpha * gravity[2] + (1 - alpha) * z;

            linearAcceleration[0] = x - gravity[0];
            linearAcceleration[1] = y - gravity[1];
            linearAcceleration[2] = z - gravity[2];

            float filteredMovement = (float) Math.sqrt(
                    linearAcceleration[0] * linearAcceleration[0] +
                    linearAcceleration[1] * linearAcceleration[1] +
                    linearAcceleration[2] * linearAcceleration[2]
            );

            if (listener != null) {
                listener.onAccelerometerUpdated(x, y, z, magnitude, filteredMovement);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
