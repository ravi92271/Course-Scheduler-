/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ravipatel
 */
public class ClassDescription {
    private String courseCode;
    private String description;
    private int seats;
    
    public ClassDescription(String courseCode, String description, int seats){
    //Pre: Need code, description, and seats
    //Post: Will initalize all respective variables
        
        this.courseCode = courseCode;
        this.description = description;
        this.seats = seats;
        
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }

    public int getSeats() {
        return seats;
    }
    
}
