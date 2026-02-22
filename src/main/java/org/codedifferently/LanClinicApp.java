package org.codedifferently;

import java.util.Scanner;

public class LanClinicApp {
    public static void main(String[] args) {

        LanClinicSystem clinic = new LanClinicSystem();
        Scanner sc = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n=== Community Clinic System ===");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Check-in Patient");
            System.out.println("4. Search Patient");
            System.out.println("5. Schedule Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. View Schedule");
            System.out.println("8. Daily Summary Report");
            System.out.println("9. Exit");
            System.out.print("Select option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter patient name: ");
                    String name = sc.nextLine();
                    clinic.addPatient(name);
                    break;

                case "2":
                    clinic.viewAllPatients();
                    break;

                case "3":
                    System.out.print("Enter patient ID or name: ");
                    String checkInID = sc.nextLine();
                    clinic.checkInPatient(checkInID);
                    break;

                case "4":
                    System.out.print("Enter patient ID or name to search: ");
                    String searchID = sc.nextLine();
                    LanPatient patient = clinic.searchPatient(searchID);
                    if (patient != null) {
                        System.out.println(patient);
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case "5":
                    System.out.print("Enter patient ID: ");
                    String pid = sc.nextLine();
                    if (clinic.searchPatient(pid) != null) {
                        clinic.scheduleAppointment(pid);
                    } else {
                        System.out.println("Patient not found. Please add the patient first.");
                    }
                    break;

                case "6":
                    System.out.print("Enter patient ID: ");
                    String cancelID = sc.nextLine();
                    clinic.cancelAppointment(cancelID);
                    break;

                case "7":
                    clinic.viewSchedule();
                    break;

                case "8":
                    clinic.dailySummary();
                    break;

                case "9":
                    running = false;
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid option, try again.");
            }
        }

        sc.close();
    }
}