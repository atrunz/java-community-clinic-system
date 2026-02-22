package org.codedifferently;

import java.time.LocalDateTime;

public class LanAppointment {
    private String patientID;
    private LocalDateTime time;
    private boolean isCompleted;

    public LanAppointment(String patientID, LocalDateTime time) {
        this.patientID = patientID;
        this.time = time;
        this.isCompleted = false;
    }

    public String getPatientID() {
        return patientID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void complete() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return "Appointment for Patient ID: " + patientID + " at " + time + " | Completed: " + isCompleted;
    }
}

