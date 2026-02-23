package org.codedifferently;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LanClinicSystem {

    private ArrayList<LanPatient> patients;
    private ArrayList<LanAppointment> appointments;
    private HashMap<LocalDateTime, Queue<String>> waitlistMap;

    public LanClinicSystem() {
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        waitlistMap = new HashMap<>();
    }

    // ---------------- PATIENT ----------------

    public void addPatient(String name) {
        LanPatient p = new LanPatient(name);
        patients.add(p);
        System.out.println("Patient added. ID: " + p.getID());
    }

    public void viewAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients.");
            return;
        }
        for (LanPatient p : patients) {
            System.out.println(p);
        }
    }

    public LanPatient searchPatient(String input) {
        for (LanPatient p : patients) {
            if (p.getID().equalsIgnoreCase(input)
                    || p.getName().equalsIgnoreCase(input)) {
                return p;
            }
        }
        return null;
    }

    public void checkInPatient(String input) {
        LanPatient p = searchPatient(input);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }

        if (p.isCheckedIn()) {
            System.out.println("Already checked in.");
        } else {
            p.checkIn();
            System.out.println("Checked in.");
        }
    }

    // ---------------- VALIDATION ----------------

    private boolean isSlotAvailable(LocalDateTime time) {
        for (LanAppointment a : appointments) {
            if (a.getTime().equals(time)) return false;
        }
        return true;
    }

    private boolean hasAppointmentSameDay(String patientID, LocalDate date) {
        for (LanAppointment a : appointments) {
            if (a.getPatientID().equals(patientID)
                    && a.getTime().toLocalDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    // ---------------- SCHEDULE ----------------

    public void scheduleAppointment(String patientID) {

        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {

            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusDays(7);

            System.out.print("Enter date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            if (date.isBefore(today) || date.isAfter(maxDate)) {
                System.out.println("Must be within 7 days.");
                return;
            }

            if (hasAppointmentSameDay(patientID, date)) {
                System.out.println("Already has appointment that day.");
                return;
            }

            ArrayList<LocalDateTime> slots = new ArrayList<>();
            LocalTime start = LocalTime.of(9, 0);
            LocalTime end = LocalTime.of(17, 0);

            while (!start.isAfter(end.minusMinutes(30))) {
                slots.add(LocalDateTime.of(date, start));
                start = start.plusMinutes(30);
            }

            System.out.println("\n--- Time Slots ---");
            for (int i = 0; i < slots.size(); i++) {
                LocalDateTime slot = slots.get(i);

                if (isSlotAvailable(slot)) {
                    System.out.println((i + 1) + ". " + slot.format(formatter) + " (Available)");
                } else {
                    System.out.println((i + 1) + ". " + slot.format(formatter) + " (Taken)");
                }
            }

            System.out.print("Choose slot #: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice < 1 || choice > slots.size()) return;

            LocalDateTime chosen = slots.get(choice - 1);

            if (isSlotAvailable(chosen)) {

                appointments.add(new LanAppointment(patientID, chosen));
                System.out.println("Appointment scheduled.");

            } else {

                System.out.print("Join waitlist? (Y/N): ");
                String ans = sc.nextLine();

                if (ans.equalsIgnoreCase("Y")) {
                    waitlistMap.putIfAbsent(chosen, new LinkedList<>());
                    waitlistMap.get(chosen).add(patientID);
                    System.out.println("Added to waitlist.");
                }
            }

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ---------------- CANCEL ----------------

    public void cancelAppointment(String patientID) {

        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {

            System.out.print("Enter date/time (yyyy-MM-dd HH:mm): ");
            LocalDateTime time =
                    LocalDateTime.parse(sc.nextLine(), formatter);

            LanAppointment remove = null;

            for (LanAppointment a : appointments) {
                if (a.getPatientID().equals(patientID)
                        && a.getTime().equals(time)) {
                    remove = a;
                    break;
                }
            }

            if (remove == null) {
                System.out.println("Not found.");
                return;
            }

            appointments.remove(remove);
            System.out.println("Cancelled.");

            // WAITLIST AUTO FILL
            if (waitlistMap.containsKey(time)) {

                Queue<String> queue = waitlistMap.get(time);

                if (!queue.isEmpty()) {

                    String next = queue.poll();
                    appointments.add(new LanAppointment(next, time));

                    System.out.println("Waitlist patient auto-booked.");

                    if (queue.isEmpty()) {
                        waitlistMap.remove(time);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Invalid format.");
        }
    }

    // ---------------- COMPLETE ----------------

    public void completeAppointment(String patientID, LocalDateTime time) {

        for (LanAppointment a : appointments) {

            if (a.getPatientID().equals(patientID)
                    && a.getTime().equals(time)) {

                LanPatient p = searchPatient(patientID);

                if (!p.isCheckedIn()) {
                    System.out.println("Patient must check in first.");
                    return;
                }

                a.complete();
                p.checkOut();
                System.out.println("Appointment completed.");
                return;
            }
        }

        System.out.println("Appointment not found.");
    }

    // ---------------- VIEW ----------------

    public void viewSchedule() {

        if (appointments.isEmpty()) {
            System.out.println("No appointments.");
            return;
        }

        appointments.sort(Comparator.comparing(LanAppointment::getTime));

        for (LanAppointment a : appointments) {
            System.out.println(a);
        }
    }

    public void dailySummary() {

        LocalDate today = LocalDate.now();

        int total = 0;
        int completed = 0;

        for (LanAppointment a : appointments) {
            if (a.getTime().toLocalDate().equals(today)) {
                total++;
                if (a.isCompleted()) completed++;
            }
        }

        System.out.println("Appointments Today: " + total);
        System.out.println("Completed Today: " + completed);
    }
}