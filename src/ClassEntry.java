/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ravipatel
 */
public class ClassEntry {
    private String semester;
    private String courseCode;
    private int seats;

    public ClassEntry(String semester, String courseCode, int seats) {
    //Pre: Get semester, courseCode, and seats
    //Post: Will assign each item to it's respective part
    
        this.semester = semester;
        this.courseCode = courseCode;
        this.seats = seats;
        
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getSeats() {
        return seats;
    }
    
    
    
    
}
