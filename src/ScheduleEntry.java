/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ravipatel
 */

import java.sql.Timestamp;

public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timeStamp;

    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp timeStamp) {
    //Pre: Get semester, courseCode, studentID, status, and timeStamp
    //Post: Will assign each variable to it's respective part
    
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }
    
    
}
