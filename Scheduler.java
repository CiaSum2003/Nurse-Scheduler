// Cianee Sumowalt
// Scheduler for Nurses
// March 31 2023

import java.util.*;

public class Scheduler {
    private static class Nurse {
        // creates a nurse with a boolean list of times she is available
        String name;
        boolean[] availability;

        Nurse(String name, int numShifts) {
            this.name = name;
            availability = new boolean[numShifts];
        }

        public int numAvailableShifts() {
            int numShifts = 0;
            for (boolean shift : availability) {
                if (shift) {
                    numShifts++;
                }
            }
            return numShifts;
        }
    }

    private static boolean giveNurseShift(Nurse[] shifts, Nurse[] nurses, int numAssigned) {
        // assigns nurses a shift based on who has the least and so on
        if (numAssigned == shifts.length) {
            return true;
        }
        int unassignedShift = -1;
        for (int i = 0; i < shifts.length; i++) {
            if (shifts[i] == null) {
                unassignedShift = i;
                break;
            }
        }

        // Sort the nurses by the number of available shifts
        Arrays.sort(nurses, Comparator.comparingInt(Nurse::numAvailableShifts));
        for (Nurse nurse : nurses) {
            if (nurse.availability[unassignedShift]) {
                boolean alreadyAssigned = false;

                for (int i = 0; i < shifts.length; i++) {
                    if (shifts[i] == nurse && i != unassignedShift) {
                        alreadyAssigned = true;
                        break;
                    }
                }
                if (!alreadyAssigned) {
                    shifts[unassignedShift] = nurse;
                    if (giveNurseShift(shifts, nurses, numAssigned + 1)) {
                        return true;
                    }
                    shifts[unassignedShift] = null;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Nurse[] nurses = new Nurse[n];
        String[] nurseOrder = new String[n]; // new array to store input order
    
        for (int i = 0; i < n; i++) {
            String name = in.next();
            int k = in.nextInt();
            Nurse nurse = new Nurse(name, n);
            for (int j = 0; j < k; j++) {
                int shift = in.nextInt();
                nurse.availability[shift] = true;
            }
            nurses[i] = nurse;
            nurseOrder[i] = name; // add nurse name to order array
        }
        in.close();
    
        Nurse[] shifts = new Nurse[n];
        if (giveNurseShift(shifts, nurses, 0)) {
            for (String name : nurseOrder) {
                for (int i = 0; i < shifts.length; i++) {
                    if (shifts[i].name.equals(name)) {
                        System.out.println(shifts[i].name + " " + i);
                        break;
                    }
                }
            }
            System.out.println();
        } else {
            System.out.println("impossible");
        }
    }
}