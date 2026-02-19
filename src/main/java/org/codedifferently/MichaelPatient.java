package org.codedifferently;

public class MichaelPatient {

    private String name;
    private String ID;
    private boolean isCheckedIn;

        public MichaelPatient(String name, String ID) {
            this.name = name;
            this.ID = ID;
            this.isCheckedIn = false;
        }

        public String getName() {
            return name;
        }

        public String getID() {
            return ID;
        }

        public boolean isCheckedIn() {
            return isCheckedIn;
        }

        public void setCheckedIn(boolean checkedIn) {
            this.isCheckedIn = checkedIn;
        }

        @Override
        public String toString() {
            return "ID: " + ID +
                    " | Name: " + name +
                    " | Checked In: " + isCheckedIn;
        }
    }




