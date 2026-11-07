package com.mycar.app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mycar.app.models.MaintenanceRecord;

import org.junit.Test;

public class MaintenanceStatusTest {

    public static boolean isMaintenanceOverdue(MaintenanceRecord record, double currentOdometer) {
        if (!record.isScheduled()) return false;
        return record.getTargetOdometer() > 0 && currentOdometer >= record.getTargetOdometer();
    }

    @Test
    public void testOverdueMaintenance() {
        MaintenanceRecord record = new MaintenanceRecord(1, 1, "Troca de Óleo", "Troca de Óleo",
                "01/01/2024", 50000, 250.0, "Oficina X", "", true, "01/06/2024", 60000);

        // Current odometer 61000 >= target 60000 -> Overdue
        assertTrue(isMaintenanceOverdue(record, 61000));
    }

    @Test
    public void testNotOverdueMaintenance() {
        MaintenanceRecord record = new MaintenanceRecord(1, 1, "Troca de Óleo", "Troca de Óleo",
                "01/01/2024", 50000, 250.0, "Oficina X", "", true, "01/06/2024", 60000);

        // Current odometer 55000 < target 60000 -> Not overdue
        assertFalse(isMaintenanceOverdue(record, 55000));
    }
}
