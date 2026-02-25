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

    public String getPatientID() { return patientID; }
    public LocalDateTime getTime() { return time; }
    public boolean isCompleted() { return isCompleted; }

    public void complete() { isCompleted = true; }

    public String toString() {
        return "PatientID: " + patientID +
                " | Time: " + time +
                " | Completed: " + isCompleted;
    }
}