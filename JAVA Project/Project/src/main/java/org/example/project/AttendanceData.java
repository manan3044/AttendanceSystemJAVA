package org.example.project;

public class AttendanceData {
    private String subjectName;
    private int classAttended;
    private int classConducted;
    private double totalPercentage;

    public AttendanceData(String subjectName, int classAttended) {
        this.subjectName = subjectName;
        this.classAttended = classAttended;

    }

    // Getters and setters
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getClassAttended() {
        return classAttended;
    }

    public void setClassAttended(int classAttended) {
        this.classAttended = classAttended;
    }

    public int getClassConducted() {
        return classConducted;
    }

    public void setClassConducted(int classConducted) {
        this.classConducted = classConducted;
    }

    public double getTotalPercentage() {
        return totalPercentage;
    }

    public void setTotalPercentage(double totalPercentage) {
        this.totalPercentage = totalPercentage;
    }
}
