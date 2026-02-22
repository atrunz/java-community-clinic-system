package org.codedifferently;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class LanClinicSystem {
    private ArrayList<LanPatient> patients;
    private ArrayList<LanAppointment> appointments;

    public LanClinicSystem() {
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    // ----------------- PATIENT METHODS -----------------

    public void addPatient(String name) {
        LanPatient patient = new LanPatient(name);
        patients.add(patient);
        System.out.println("Patient added successfully. ID: " + patient.getID());
    }

    public void viewAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients in system.");
            return;
        }
        for (LanPatient p : patients) {
            System.out.println(p);
        }
    }

    public LanPatient searchPatient(String idOrName) {
        for (LanPatient p : patients) {
            if (p.getID().equalsIgnoreCase(idOrName) || p.getName().equalsIgnoreCase(idOrName)) {
                return p;
            }
        }
        return null;
    }

    public void checkInPatient(String idOrName) {
        LanPatient p = searchPatient(idOrName);
        if (p != null) {
            if (!p.isCheckedIn()) {
                p.checkIn();
                System.out.println("Patient " + p.getName() + " checked in successfully.");
            } else {
                System.out.println("Patient is already checked in.");
            }
        } else {
            System.out.println("Patient not found.");
        }
    }

    // ----------------- APPOINTMENT METHODS -----------------

    public void scheduleAppointment(String patientID) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(7);

        System.out.println("Schedule an appointment for Patient ID: " + patientID);
        System.out.println("Appointments can only be scheduled within the next 7 days.");

        LocalDate chosenDate = null;
        while (true) {
            try {
                System.out.print("Enter appointment date (yyyy-MM-dd): ");
                String dateInput = sc.nextLine();
                chosenDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                if (chosenDate.isBefore(today) || chosenDate.isAfter(maxDate)) {
                    System.out.println("Date must be within the next 7 days!");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format.");
            }
        }

        ArrayList<LocalDateTime> slots = new ArrayList<>();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);
        while (!start.isAfter(end.minusMinutes(30))) {
            slots.add(LocalDateTime.of(chosenDate, start));
            start = start.plusMinutes(30);
        }

        ArrayList<LocalDateTime> availableSlots = new ArrayList<>();
        for (LocalDateTime slot : slots) {
            boolean taken = false;
            for (LanAppointment a : appointments) {
                if (a.getTime().equals(slot)) {
                    taken = true;
                    break;
                }
            }
            if (!taken) availableSlots.add(slot);
        }

        if (availableSlots.isEmpty()) {
            System.out.println("No available slots on this day. Choose another day.");
            return;
        }

        System.out.println("Available time slots:");
        int idx = 1;
        for (LocalDateTime slot : availableSlots) {
            System.out.println(idx + ". " + slot.format(formatter));
            idx++;
        }

        int slotChoice = -1;
        while (true) {
            try {
                System.out.print("Select a slot number: ");
                slotChoice = Integer.parseInt(sc.nextLine());
                if (slotChoice < 1 || slotChoice > availableSlots.size()) {
                    System.out.println("Invalid choice. Try again.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }

        LanAppointment appointment = new LanAppointment(patientID, availableSlots.get(slotChoice - 1));
        appointments.add(appointment);
        System.out.println("Appointment scheduled for " + availableSlots.get(slotChoice - 1).format(formatter));
    }

    public void cancelAppointment(String patientID) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.print("Enter appointment date/time to cancel (yyyy-MM-dd HH:mm): ");
        String input = sc.nextLine();
        try {
            LocalDateTime time = LocalDateTime.parse(input, formatter);
            LanAppointment toRemove = null;
            for (LanAppointment a : appointments) {
                if (a.getPatientID().equalsIgnoreCase(patientID) && a.getTime().equals(time)) {
                    toRemove = a;
                    break;
                }
            }
            if (toRemove != null) {
                appointments.remove(toRemove);
                System.out.println("Appointment cancelled successfully.");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (Exception e) {
            System.out.println("Invalid date/time format.");
        }
    }

    public void viewSchedule() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        System.out.println("=== All Appointments ===");
        for (LanAppointment a : appointments) {
            System.out.println(a);
        }
    }

    public void dailySummary() {
        System.out.println("=== Daily Summary ===");
        System.out.println("Total Patients: " + patients.size());
        int checkedIn = 0;
        for (LanPatient p : patients) {
            if (p.isCheckedIn()) checkedIn++;
        }
        System.out.println("Checked-in Patients: " + checkedIn);
        System.out.println("Appointments Today: " + appointments.size());
        int completed = 0;
        for (LanAppointment a : appointments) {
            if (a.isCompleted()) completed++;
        }
        System.out.println("Completed Appointments: " + completed);
    }
}

