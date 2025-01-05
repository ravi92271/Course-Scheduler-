/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ravipatel
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.sql.ResultSet;

public class CourseQueries {
    private static Connection connection;
    private static CourseEntry course;
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static ResultSet result;
    
    public static void addCourse(CourseEntry course){
    //Pre: Will take in a course
    //Post: Will add it in the database
    
        //Establish a connection first
        connection = DBConnection.getConnection();
        //Try catch for adding a course
        try{
            //Have a prepared statement to go into the course table and insert a value
            addCourse = connection.prepareStatement("INSERT into app.course (coursecode, description) values (?, ?)");
            
            //Add an object into the course
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2, course.getDescription());
            
            //Execute that update
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    
    
    }
    
    public static ArrayList<String> getAllCourseCodes(){
    //Pre: Takes in nothing
    //Post: Returns an array list of strings of the course information
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Establish a new course arraylist to return
        ArrayList<String> course = new ArrayList<String>();
        
        //Try catch to insert courses inside
        try{
            //Establish a connection using a prepared statement and extract courses
            getCourseList = connection.prepareStatement("SELECT coursecode FROM app.course ORDER BY coursecode");
            
            //Store that information in a result set
            result = getCourseList.executeQuery();
            
            //Iterate through the results and put into arraylist
            while(result.next()){
                course.add(result.getString(1));
            }
        
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        return course;
    }
    
}
