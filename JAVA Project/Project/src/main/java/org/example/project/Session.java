package org.example.project;

public class Session {
    private static Session session = null;
    private int facultyID;

    private Session(int facultyID) {
        this.facultyID = facultyID;
    }

    public static Session getInstance(int facultyID) {
        if (session == null) {
            session = new Session(facultyID);
        }
        return session;
    }

    public int getFacultyID() {
        return this.facultyID;
    }

    public void clearSession() {
        session=null;}
}