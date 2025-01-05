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

public class ClassQueries {
    
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement getCourseList;
    private static PreparedStatement getClassSeatList;
    private static PreparedStatement dropClass;
    private static ResultSet result;
    
    public static void addClass(ClassEntry newClass){
    //Pre: Takes in a new class
    //Post: Will add it to the DB
        
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Try catch for adding a class
        try{
            //Have a prepared statement to go into the class table and insert a value
            addClass = connection.prepareStatement("INSERT into app.class (semester, coursecode, seats) values (?, ?, ?)");
            
            //Add an object to class table
            addClass.setString(1, newClass.getSemester());
            addClass.setString(2, newClass.getCourseCode());
            addClass.setInt(3, newClass.getSeats());
            
            //Execute that update
            addClass.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester){
    //Pre: Needs the semester to pinpoint the courses in that semester
    //Post: Will return an arraylist of strings
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Create arraylist of strings
        ArrayList<String> classes = new ArrayList<String>();
        
        //Try catch to insert classes inside
        try{
            //Establish a connection using a prepared statement and extract classes
            //Note: You have to use WHERE semester = ? to specify what semester it's from
            getCourseList = connection.prepareStatement("SELECT coursecode FROM app.class WHERE semester = ? ORDER BY coursecode");
            
            //Set the course parameter in the query
            getCourseList.setString(1, semester);
            
            //Add to result set
            result = getCourseList.executeQuery();
            
            //Iterate through while loop to add to string
            while(result.next()){
                classes.add(result.getString(1));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        return classes;
    }    
    
    public static int getClassSeats(String semester, String courseCode){
    //Pre: Need the semester and courseCode to pinpoint the exact class
    //Post: Will get class seats
    
        //Establish a oonnection
        connection = DBConnection.getConnection();
        
        //Create an int for seats
        int seats = 0;
        
        //Try catch to get seats
        try{
            //Establish a connection using a prepared statement and extract
            //Note: You have to use WHERE semester = ? AND coursecode = ? to specify where it's coming from
            getClassSeatList = connection.prepareStatement("SELECT semester, coursecode, seats from app.class "
                                                            + "WHERE semester = ? AND coursecode = ? ORDER BY seats");
            
            //Set semester and courseCode in the query
            getClassSeatList.setString(1, semester);
            getClassSeatList.setString(2, courseCode);
            
            //Add to result set
            result = getClassSeatList.executeQuery();
            
            //Get the seat number
            while(result.next()){
                seats = result.getInt(3);
            }
           
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    
        return seats;
    }
    
    public static void dropClass(String semester, String courseCode){
    //Pre: Need the semester and course code
    //Post: Will drop the class and all the students inside
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Try catch to remove the class and students
        try{
            //Get the class you want to drop
            dropClass = connection.prepareStatement("DELETE FROM app.class WHERE semester = ? AND courseCode = ?");
            
            //Set semester and coursecode in query
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);

            //Execute the update
            dropClass.executeUpdate();
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    
    
    
    
    }
}
