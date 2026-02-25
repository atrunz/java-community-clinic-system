package org.codedifferently;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LanClinicApp {

    public static void main(String[] args) {

        LanClinicSystem clinic = new LanClinicSystem();
        Scanner sc = new Scanner(System.in);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        boolean running = true;

        while (running) {

            System.out.println("\n=== CLINIC MENU ===");
            System.out.println("1 Add Patient");
            System.out.println("2 View Patients");
            System.out.println("3 Check In Patient");
            System.out.println("4 Search Patient");
            System.out.println("5 Schedule Appointment");
            System.out.println("6 Cancel Appointment");
            System.out.println("7 View Schedule");
            System.out.println("8 Daily Report");
            System.out.println("9 Exit");
            System.out.println("10 Complete Appointment");


            System.out.print("Choice: ");
            System.out.println("===========================================1");
            String choice = sc.nextLine();

            switch(choice) {

                case "1":
                    System.out.print("Name: ");
                    clinic.addPatient(sc.nextLine());
                    break;

                case "2":
                    clinic.viewAllPatients();
                    break;

                case "3":
                    System.out.print("ID or Name: ");
                    clinic.checkInPatient(sc.nextLine());
                    break;

                case "4":
                    System.out.println("================================");
                    System.out.print("ID or Name: ");
                    System.out.println(clinic.searchPatient(sc.nextLine()));
                    break;

                case "5":
                    System.out.println("================================");
                    System.out.print("Patient ID: ");
                    clinic.scheduleAppointment(sc.nextLine());
                    break;

                case "6":
                    System.out.println("=================================");
                    System.out.print("Patient ID: ");
                    clinic.cancelAppointment(sc.nextLine());
                    break;

                case "7":
                    clinic.viewSchedule();
                    break;

                case "8":
                    clinic.dailySummary();
                    break;

                case "9":
                    running = false;
                    break;

                case "10":
                    try {
                        System.out.println("=============================");
                        System.out.print("Patient ID: ");
                        String id = sc.nextLine();

                        System.out.print("DateTime yyyy-MM-dd HH:mm: ");
                        LocalDateTime time =
                                LocalDateTime.parse(sc.nextLine(), formatter);

                        clinic.completeAppointment(id, time);

                    } catch(Exception e) {
                        System.out.println("Invalid format.");
                    }
                    break;
            }
        }

        sc.close();
    }
}