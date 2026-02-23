package org.codedifferently;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LanAppointment {

    private String patientID;
    private LocalDateTime time;
    private boolean isCompleted;
    private LocalDate date;

    public LanAppointment(String patientID, LocalDateTime time, LocalDate date) {
        this.patientID = patientID;
        this.time = time;
        this.date = date;
        this.isCompleted = false;
    }

    public String getPatientID() { return patientID; }
    public LocalDateTime getTime() { return time; }
    public LocalDate getDate() { return date;}
    public boolean isCompleted() { return isCompleted; }

    public void complete() { isCompleted = true; }

    public String toString() {
        return "PatientID: " + patientID +
                " | Time: " + time +
                " | Completed: " + isCompleted;
    }
}
