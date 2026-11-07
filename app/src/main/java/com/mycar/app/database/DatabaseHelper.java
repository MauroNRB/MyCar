package com.mycar.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mycar.app.models.AccelerationTestRecord;
import com.mycar.app.models.MaintenanceRecord;
import com.mycar.app.models.MileageRecord;
import com.mycar.app.models.TripRecord;
import com.mycar.app.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mycar.db";
    private static final int DATABASE_VERSION = 1;

    // Table Vehicles
    public static final String TABLE_VEHICLES = "vehicles";
    public static final String COL_VEHICLE_ID = "id";
    public static final String COL_VEHICLE_NAME = "name";
    public static final String COL_VEHICLE_MANUFACTURER = "manufacturer";
    public static final String COL_VEHICLE_MODEL = "model";
    public static final String COL_VEHICLE_YEAR_FAB = "year_fab";
    public static final String COL_VEHICLE_YEAR_MODEL = "year_model";
    public static final String COL_VEHICLE_PLATE = "plate";
    public static final String COL_VEHICLE_FUEL_TYPE = "fuel_type";
    public static final String COL_VEHICLE_CURRENT_ODOMETER = "current_odometer";
    public static final String COL_VEHICLE_ENGINE_INFO = "engine_info";

    // Table Mileage Records
    public static final String TABLE_MILEAGE = "mileage_records";
    public static final String COL_MILEAGE_ID = "id";
    public static final String COL_MILEAGE_VEHICLE_ID = "vehicle_id";
    public static final String COL_MILEAGE_ODOMETER = "odometer";
    public static final String COL_MILEAGE_DATE = "date_timestamp";
    public static final String COL_MILEAGE_NOTES = "notes";
    public static final String COL_MILEAGE_DIFFERENCE = "difference";

    // Table Maintenance Records
    public static final String TABLE_MAINTENANCE = "maintenance_records";
    public static final String COL_MAINT_ID = "id";
    public static final String COL_MAINT_VEHICLE_ID = "vehicle_id";
    public static final String COL_MAINT_TITLE = "title";
    public static final String COL_MAINT_CATEGORY = "category";
    public static final String COL_MAINT_DATE = "date_performed";
    public static final String COL_MAINT_ODOMETER = "odometer";
    public static final String COL_MAINT_COST = "cost";
    public static final String COL_MAINT_WORKSHOP = "workshop";
    public static final String COL_MAINT_NOTES = "notes";
    public static final String COL_MAINT_IS_SCHEDULED = "is_scheduled";
    public static final String COL_MAINT_TARGET_DATE = "target_date";
    public static final String COL_MAINT_TARGET_ODOMETER = "target_odometer";

    // Table Trips
    public static final String TABLE_TRIPS = "trips";
    public static final String COL_TRIP_ID = "id";
    public static final String COL_TRIP_VEHICLE_ID = "vehicle_id";
    public static final String COL_TRIP_START_TIME = "start_time";
    public static final String COL_TRIP_END_TIME = "end_time";
    public static final String COL_TRIP_DISTANCE = "distance_km";
    public static final String COL_TRIP_AVG_SPEED = "avg_speed_kmh";
    public static final String COL_TRIP_MAX_SPEED = "max_speed_kmh";

    // Table Acceleration Tests
    public static final String TABLE_ACCELERATION = "acceleration_tests";
    public static final String COL_ACCEL_ID = "id";
    public static final String COL_ACCEL_VEHICLE_ID = "vehicle_id";
    public static final String COL_ACCEL_ELAPSED_TIME = "elapsed_time_seconds";
    public static final String COL_ACCEL_TIMESTAMP = "timestamp";
    public static final String COL_ACCEL_INIT_SPEED = "initial_speed_kmh";
    public static final String COL_ACCEL_FINAL_SPEED = "final_speed_kmh";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createVehicles = "CREATE TABLE " + TABLE_VEHICLES + " ("
                + COL_VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_VEHICLE_NAME + " TEXT NOT NULL, "
                + COL_VEHICLE_MANUFACTURER + " TEXT, "
                + COL_VEHICLE_MODEL + " TEXT, "
                + COL_VEHICLE_YEAR_FAB + " INTEGER, "
                + COL_VEHICLE_YEAR_MODEL + " INTEGER, "
                + COL_VEHICLE_PLATE + " TEXT, "
                + COL_VEHICLE_FUEL_TYPE + " TEXT, "
                + COL_VEHICLE_CURRENT_ODOMETER + " REAL, "
                + COL_VEHICLE_ENGINE_INFO + " TEXT)";

        String createMileage = "CREATE TABLE " + TABLE_MILEAGE + " ("
                + COL_MILEAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MILEAGE_VEHICLE_ID + " INTEGER NOT NULL, "
                + COL_MILEAGE_ODOMETER + " REAL NOT NULL, "
                + COL_MILEAGE_DATE + " TEXT NOT NULL, "
                + COL_MILEAGE_NOTES + " TEXT, "
                + COL_MILEAGE_DIFFERENCE + " REAL, "
                + "FOREIGN KEY(" + COL_MILEAGE_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLES + "(" + COL_VEHICLE_ID + ") ON DELETE CASCADE)";

        String createMaintenance = "CREATE TABLE " + TABLE_MAINTENANCE + " ("
                + COL_MAINT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MAINT_VEHICLE_ID + " INTEGER NOT NULL, "
                + COL_MAINT_TITLE + " TEXT NOT NULL, "
                + COL_MAINT_CATEGORY + " TEXT NOT NULL, "
                + COL_MAINT_DATE + " TEXT, "
                + COL_MAINT_ODOMETER + " REAL, "
                + COL_MAINT_COST + " REAL, "
                + COL_MAINT_WORKSHOP + " TEXT, "
                + COL_MAINT_NOTES + " TEXT, "
                + COL_MAINT_IS_SCHEDULED + " INTEGER DEFAULT 0, "
                + COL_MAINT_TARGET_DATE + " TEXT, "
                + COL_MAINT_TARGET_ODOMETER + " REAL, "
                + "FOREIGN KEY(" + COL_MAINT_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLES + "(" + COL_VEHICLE_ID + ") ON DELETE CASCADE)";

        String createTrips = "CREATE TABLE " + TABLE_TRIPS + " ("
                + COL_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TRIP_VEHICLE_ID + " INTEGER NOT NULL, "
                + COL_TRIP_START_TIME + " TEXT NOT NULL, "
                + COL_TRIP_END_TIME + " TEXT, "
                + COL_TRIP_DISTANCE + " REAL, "
                + COL_TRIP_AVG_SPEED + " REAL, "
                + COL_TRIP_MAX_SPEED + " REAL, "
                + "FOREIGN KEY(" + COL_TRIP_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLES + "(" + COL_VEHICLE_ID + ") ON DELETE CASCADE)";

        String createAcceleration = "CREATE TABLE " + TABLE_ACCELERATION + " ("
                + COL_ACCEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ACCEL_VEHICLE_ID + " INTEGER NOT NULL, "
                + COL_ACCEL_ELAPSED_TIME + " REAL NOT NULL, "
                + COL_ACCEL_TIMESTAMP + " TEXT NOT NULL, "
                + COL_ACCEL_INIT_SPEED + " REAL, "
                + COL_ACCEL_FINAL_SPEED + " REAL, "
                + "FOREIGN KEY(" + COL_ACCEL_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLES + "(" + COL_VEHICLE_ID + ") ON DELETE CASCADE)";

        db.execSQL(createVehicles);
        db.execSQL(createMileage);
        db.execSQL(createMaintenance);
        db.execSQL(createTrips);
        db.execSQL(createAcceleration);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCELERATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAINTENANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MILEAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        onCreate(db);
    }

    // --- Vehicle CRUD Operations ---

    public long insertVehicle(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_VEHICLE_NAME, vehicle.getName());
        cv.put(COL_VEHICLE_MANUFACTURER, vehicle.getManufacturer());
        cv.put(COL_VEHICLE_MODEL, vehicle.getModel());
        cv.put(COL_VEHICLE_YEAR_FAB, vehicle.getYearFab());
        cv.put(COL_VEHICLE_YEAR_MODEL, vehicle.getYearModel());
        cv.put(COL_VEHICLE_PLATE, vehicle.getPlate());
        cv.put(COL_VEHICLE_FUEL_TYPE, vehicle.getFuelType());
        cv.put(COL_VEHICLE_CURRENT_ODOMETER, vehicle.getCurrentOdometer());
        cv.put(COL_VEHICLE_ENGINE_INFO, vehicle.getEngineInfo());

        long id = db.insert(TABLE_VEHICLES, null, cv);
        vehicle.setId(id);
        return id;
    }

    public boolean updateVehicle(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_VEHICLE_NAME, vehicle.getName());
        cv.put(COL_VEHICLE_MANUFACTURER, vehicle.getManufacturer());
        cv.put(COL_VEHICLE_MODEL, vehicle.getModel());
        cv.put(COL_VEHICLE_YEAR_FAB, vehicle.getYearFab());
        cv.put(COL_VEHICLE_YEAR_MODEL, vehicle.getYearModel());
        cv.put(COL_VEHICLE_PLATE, vehicle.getPlate());
        cv.put(COL_VEHICLE_FUEL_TYPE, vehicle.getFuelType());
        cv.put(COL_VEHICLE_CURRENT_ODOMETER, vehicle.getCurrentOdometer());
        cv.put(COL_VEHICLE_ENGINE_INFO, vehicle.getEngineInfo());

        int rows = db.update(TABLE_VEHICLES, cv, COL_VEHICLE_ID + "=?",
                new String[]{String.valueOf(vehicle.getId())});
        return rows > 0;
    }

    public Vehicle getDefaultVehicle() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VEHICLES, null, null, null, null, null, COL_VEHICLE_ID + " ASC", "1");
        Vehicle vehicle = null;
        if (cursor != null && cursor.moveToFirst()) {
            vehicle = cursorToVehicle(cursor);
            cursor.close();
        }
        return vehicle;
    }

    private Vehicle cursorToVehicle(Cursor cursor) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_VEHICLE_ID)));
        vehicle.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_VEHICLE_NAME)));
        vehicle.setManufacturer(cursor.getString(cursor.getColumnIndexOrThrow(COL_VEHICLE_MANUFACTURER)));
        vehicle.setModel(cursor.getString(cursor.getColumnIndexOrThrow(COL_VEHICLE_MODEL)));
        vehicle.setYearFab(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_YEAR_FAB)));
        vehicle.setYearModel(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_YEAR_MODEL)));
        vehicle.setPlate(cursor.getString(cursor.getColumnIndexOrThrow(COL_VEHICLE_PLATE)));
        vehicle.setFuelType(cursor.getString(cursor.getColumnIndexOrThrow(COL_VEHICLE_FUEL_TYPE)));
        vehicle.setCurrentOdometer(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_VEHICLE_CURRENT_ODOMETER)));
        vehicle.setEngineInfo(cursor.getString(cursor.getColumnIndexOrThrow(COL_VEHICLE_ENGINE_INFO)));
        return vehicle;
    }

    // --- Mileage Records Operations ---

    public long insertMileageRecord(MileageRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Calculate difference with previous record
        double prevOdometer = 0;
        MileageRecord last = getLastMileageRecord(record.getVehicleId());
        if (last != null) {
            prevOdometer = last.getOdometer();
        } else {
            Vehicle v = getDefaultVehicle();
            if (v != null) prevOdometer = v.getCurrentOdometer();
        }

        double diff = Math.max(0, record.getOdometer() - prevOdometer);
        record.setDifference(diff);

        ContentValues cv = new ContentValues();
        cv.put(COL_MILEAGE_VEHICLE_ID, record.getVehicleId());
        cv.put(COL_MILEAGE_ODOMETER, record.getOdometer());
        cv.put(COL_MILEAGE_DATE, record.getDateTimestamp());
        cv.put(COL_MILEAGE_NOTES, record.getNotes());
        cv.put(COL_MILEAGE_DIFFERENCE, record.getDifference());

        long id = db.insert(TABLE_MILEAGE, null, cv);
        record.setId(id);

        // Update current odometer on vehicle
        updateVehicleOdometer(record.getVehicleId(), record.getOdometer());

        return id;
    }

    public MileageRecord getLastMileageRecord(long vehicleId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MILEAGE, null, COL_MILEAGE_VEHICLE_ID + "=?",
                new String[]{String.valueOf(vehicleId)}, null, null, COL_MILEAGE_ID + " DESC", "1");
        MileageRecord record = null;
        if (cursor != null && cursor.moveToFirst()) {
            record = cursorToMileageRecord(cursor);
            cursor.close();
        }
        return record;
    }

    public List<MileageRecord> getMileageRecordsForVehicle(long vehicleId) {
        List<MileageRecord> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MILEAGE, null, COL_MILEAGE_VEHICLE_ID + "=?",
                new String[]{String.valueOf(vehicleId)}, null, null, COL_MILEAGE_ID + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursorToMileageRecord(cursor));
            }
            cursor.close();
        }
        return list;
    }

    public void updateVehicleOdometer(long vehicleId, double newOdometer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_VEHICLE_CURRENT_ODOMETER, newOdometer);
        db.update(TABLE_VEHICLES, cv, COL_VEHICLE_ID + "=?", new String[]{String.valueOf(vehicleId)});
    }

    private MileageRecord cursorToMileageRecord(Cursor cursor) {
        MileageRecord record = new MileageRecord();
        record.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_MILEAGE_ID)));
        record.setVehicleId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_MILEAGE_VEHICLE_ID)));
        record.setOdometer(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_MILEAGE_ODOMETER)));
        record.setDateTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(COL_MILEAGE_DATE)));
        record.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(COL_MILEAGE_NOTES)));
        record.setDifference(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_MILEAGE_DIFFERENCE)));
        return record;
    }

    // --- Maintenance CRUD Operations ---

    public long insertMaintenanceRecord(MaintenanceRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MAINT_VEHICLE_ID, record.getVehicleId());
        cv.put(COL_MAINT_TITLE, record.getTitle());
        cv.put(COL_MAINT_CATEGORY, record.getCategory());
        cv.put(COL_MAINT_DATE, record.getDatePerformed());
        cv.put(COL_MAINT_ODOMETER, record.getOdometer());
        cv.put(COL_MAINT_COST, record.getCost());
        cv.put(COL_MAINT_WORKSHOP, record.getWorkshop());
        cv.put(COL_MAINT_NOTES, record.getNotes());
        cv.put(COL_MAINT_IS_SCHEDULED, record.isScheduled() ? 1 : 0);
        cv.put(COL_MAINT_TARGET_DATE, record.getTargetDate());
        cv.put(COL_MAINT_TARGET_ODOMETER, record.getTargetOdometer());

        long id = db.insert(TABLE_MAINTENANCE, null, cv);
        record.setId(id);
        return id;
    }

    public boolean updateMaintenanceRecord(MaintenanceRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MAINT_TITLE, record.getTitle());
        cv.put(COL_MAINT_CATEGORY, record.getCategory());
        cv.put(COL_MAINT_DATE, record.getDatePerformed());
        cv.put(COL_MAINT_ODOMETER, record.getOdometer());
        cv.put(COL_MAINT_COST, record.getCost());
        cv.put(COL_MAINT_WORKSHOP, record.getWorkshop());
        cv.put(COL_MAINT_NOTES, record.getNotes());
        cv.put(COL_MAINT_IS_SCHEDULED, record.isScheduled() ? 1 : 0);
        cv.put(COL_MAINT_TARGET_DATE, record.getTargetDate());
        cv.put(COL_MAINT_TARGET_ODOMETER, record.getTargetOdometer());

        int rows = db.update(TABLE_MAINTENANCE, cv, COL_MAINT_ID + "=?",
                new String[]{String.valueOf(record.getId())});
        return rows > 0;
    }

    public boolean deleteMaintenanceRecord(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_MAINTENANCE, COL_MAINT_ID + "=?",
                new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public List<MaintenanceRecord> getMaintenanceRecordsForVehicle(long vehicleId, String categoryFilter) {
        List<MaintenanceRecord> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_MAINT_VEHICLE_ID + "=?";
        List<String> selectionArgsList = new ArrayList<>();
        selectionArgsList.add(String.valueOf(vehicleId));

        if (categoryFilter != null && !categoryFilter.isEmpty() && !categoryFilter.equals("Todas") && !categoryFilter.equals("Todas as categorias")) {
            selection += " AND " + COL_MAINT_CATEGORY + "=?";
            selectionArgsList.add(categoryFilter);
        }

        Cursor cursor = db.query(TABLE_MAINTENANCE, null, selection,
                selectionArgsList.toArray(new String[0]), null, null, COL_MAINT_ID + " DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursorToMaintenanceRecord(cursor));
            }
            cursor.close();
        }
        return list;
    }

    public int getPendingMaintenancesCount(long vehicleId, double currentOdometer) {
        List<MaintenanceRecord> all = getMaintenanceRecordsForVehicle(vehicleId, null);
        int count = 0;
        for (MaintenanceRecord r : all) {
            if (r.isScheduled()) {
                if (r.getTargetOdometer() > 0 && currentOdometer >= r.getTargetOdometer()) {
                    count++;
                }
            }
        }
        return count;
    }

    private MaintenanceRecord cursorToMaintenanceRecord(Cursor cursor) {
        MaintenanceRecord record = new MaintenanceRecord();
        record.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_MAINT_ID)));
        record.setVehicleId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_MAINT_VEHICLE_ID)));
        record.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_MAINT_TITLE)));
        record.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COL_MAINT_CATEGORY)));
        record.setDatePerformed(cursor.getString(cursor.getColumnIndexOrThrow(COL_MAINT_DATE)));
        record.setOdometer(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_MAINT_ODOMETER)));
        record.setCost(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_MAINT_COST)));
        record.setWorkshop(cursor.getString(cursor.getColumnIndexOrThrow(COL_MAINT_WORKSHOP)));
        record.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(COL_MAINT_NOTES)));
        record.setScheduled(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MAINT_IS_SCHEDULED)) == 1);
        record.setTargetDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_MAINT_TARGET_DATE)));
        record.setTargetOdometer(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_MAINT_TARGET_ODOMETER)));
        return record;
    }

    // --- Trip Records Operations ---

    public long insertTripRecord(TripRecord trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TRIP_VEHICLE_ID, trip.getVehicleId());
        cv.put(COL_TRIP_START_TIME, trip.getStartTime());
        cv.put(COL_TRIP_END_TIME, trip.getEndTime());
        cv.put(COL_TRIP_DISTANCE, trip.getDistanceKm());
        cv.put(COL_TRIP_AVG_SPEED, trip.getAvgSpeedKmh());
        cv.put(COL_TRIP_MAX_SPEED, trip.getMaxSpeedKmh());

        long id = db.insert(TABLE_TRIPS, null, cv);
        trip.setId(id);
        return id;
    }

    public List<TripRecord> getTripsForVehicle(long vehicleId) {
        List<TripRecord> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRIPS, null, COL_TRIP_VEHICLE_ID + "=?",
                new String[]{String.valueOf(vehicleId)}, null, null, COL_TRIP_ID + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                TripRecord record = new TripRecord();
                record.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_TRIP_ID)));
                record.setVehicleId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_TRIP_VEHICLE_ID)));
                record.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_TRIP_START_TIME)));
                record.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_TRIP_END_TIME)));
                record.setDistanceKm(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TRIP_DISTANCE)));
                record.setAvgSpeedKmh(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TRIP_AVG_SPEED)));
                record.setMaxSpeedKmh(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TRIP_MAX_SPEED)));
                list.add(record);
            }
            cursor.close();
        }
        return list;
    }

    public double getDistanceToday(long vehicleId) {
        List<TripRecord> trips = getTripsForVehicle(vehicleId);
        double total = 0;
        for (TripRecord t : trips) {
            total += t.getDistanceKm();
        }
        return total;
    }

    // --- Acceleration Test Operations ---

    public long insertAccelerationTestRecord(AccelerationTestRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ACCEL_VEHICLE_ID, record.getVehicleId());
        cv.put(COL_ACCEL_ELAPSED_TIME, record.getElapsedTimeSeconds());
        cv.put(COL_ACCEL_TIMESTAMP, record.getTimestamp());
        cv.put(COL_ACCEL_INIT_SPEED, record.getInitialSpeedKmh());
        cv.put(COL_ACCEL_FINAL_SPEED, record.getFinalSpeedKmh());

        long id = db.insert(TABLE_ACCELERATION, null, cv);
        record.setId(id);
        return id;
    }

    public List<AccelerationTestRecord> getAccelerationTestsForVehicle(long vehicleId) {
        List<AccelerationTestRecord> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCELERATION, null, COL_ACCEL_VEHICLE_ID + "=?",
                new String[]{String.valueOf(vehicleId)}, null, null, COL_ACCEL_ID + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AccelerationTestRecord record = new AccelerationTestRecord();
                record.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_ACCEL_ID)));
                record.setVehicleId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_ACCEL_VEHICLE_ID)));
                record.setElapsedTimeSeconds(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ACCEL_ELAPSED_TIME)));
                record.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(COL_ACCEL_TIMESTAMP)));
                record.setInitialSpeedKmh(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ACCEL_INIT_SPEED)));
                record.setFinalSpeedKmh(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ACCEL_FINAL_SPEED)));
                list.add(record);
            }
            cursor.close();
        }
        return list;
    }
}
