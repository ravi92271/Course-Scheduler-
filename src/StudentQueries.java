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


public class StudentQueries {
    private static Connection connection;
    private static StudentEntry student;
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudentList;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudentOverall;
    private static ResultSet result;
    
    public static void addStudent(StudentEntry student){
    //Pre: Need the student
    //Post: Will add the student
    
        //Connect to DB
        connection = DBConnection.getConnection();
        
        //Try catch to add student 
        try{
            //Have a prepared statement to go into the student table and insert a value
            addStudent = connection.prepareStatement("INSERT into app.student (studentid, firstname, lastname) "
                                                     + "values (?, ?, ?)");
            
            //Add an object to the table
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            
            //Excute that update
            addStudent.executeUpdate();
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
    //Pre: Nothing is needed
    //Post: Will return an arraylist of students
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Make an arraylist of strings
        ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
    
        //Try catch to get all students
        try{
            //Establish a connection using a prepared statement and extract classes
            //Note: You have to use WHERE student = ? to specify what semester it's from
            getStudentList = connection.prepareStatement("SELECT studentid, firstname, lastname FROM app.student ORDER BY studentid");
            
            //Store into result
            result = getStudentList.executeQuery();
            
            //While loop to get all students
            while(result.next()){
                //Get all information from result
                String id = result.getString("studentid");
                String fName = result.getString("firstname");
                String lName = result.getString("lastname");
                
                //Store into tempStudent
                StudentEntry tempStudent = new StudentEntry(id, fName, lName);
                
                //Store into students
                student.add(tempStudent);
            }
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        
        }
    
        return student;
    }
    
    public static StudentEntry getStudent(String studentID){
    //Pre: Need the studentID
    //Post: Will get the student
    
        //Establish connection to DB
        connection = DBConnection.getConnection();
        
        
        //Try catch to get student
        try{
            //Establish a connection using a prepared statement
            getStudent = connection.prepareStatement("SELECT studentid, firstname, lastname FROM app.student WHERE "
                                                    + "studentid = ?");
        
            //Set the object
            getStudent.setString(1, studentID);
            
            //Execute query
            result = getStudent.executeQuery();
            
            //Create the student
            StudentEntry student;
            
            while(result.next()){
                //Get all results
                String ID = result.getString("studentid");
                String fName = result.getString("firstname");
                String lName = result.getString("lastname");
                
                //Store in student
                student = new StudentEntry(ID, fName, lName);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        return student;
    }
    
    public static void dropStudent(String studentID){
    //Pre: Need the studentID
    //Post: Will drop the student
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Try catch to drop the student
        try{
            //Establish a connection using a prepare statement to drop the student
            dropStudentOverall = connection.prepareStatement("DELETE FROM app.student WHERE studentid = ?");
            
            //Set studentid in query
            dropStudentOverall.setString(1, studentID);
            
            //Execute updates
            dropStudentOverall.executeUpdate();
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    
    }
}
