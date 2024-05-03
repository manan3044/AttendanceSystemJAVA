package org.example.project;

public class UpdateTable {
    private String serial;
    private String name;
    private String attendance;
    private String conducted;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getConducted() {
        return conducted;
    }

    public void setConducted(String conducted) {
        this.conducted = conducted;
    }

    public UpdateTable(String serial,String name, String attendance,String conducted) {
        this.serial = serial;
        this.attendance = attendance;
        this.name = name;
        this.conducted = conducted;

    }

}
