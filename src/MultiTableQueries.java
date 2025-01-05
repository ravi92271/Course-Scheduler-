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


public class MultiTableQueries {
    private static Connection connection;
    private static PreparedStatement getAllClassDescriptionList;
    private static PreparedStatement getScheduledStudentsID;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsID;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static ResultSet result;
    
    public static ArrayList<ClassDescription> getAllClassDescription(String semester){
    //Pre: Get the semester
    //Post: Will return all class descriptions
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Create arraylist of class descriptions
        ArrayList<ClassDescription> description = new ArrayList<ClassDescription>();
        
        //Try catch to get all descriptions
        try{
            //Note: Can do inner join instead
            getAllClassDescriptionList = connection.prepareStatement("SELECT app.class.courseCode, description, seats "
                                                                    + "FROM app.class, app.course WHERE semester = ? "
                                                                    + "AND app.class.courseCode = app.course.courseCode "
                                                                    + "ORDER BY app.class.courseCode");
            
            
            //Set semester
            getAllClassDescriptionList.setString(1, semester);
            
            //Exectute query
            result = getAllClassDescriptionList.executeQuery();
            
            while(result.next()){
                //Set temp variables
                String courseCode = result.getString("coursecode");
                String desc = result.getString("description");
                int seats = result.getInt("seats");
                
                //Insert results into temp ClassDescription
                ClassDescription tempDescription = new ClassDescription(courseCode, desc, seats);
                
                //Put temp into array list
                description.add(tempDescription);
            
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        return description;
    }  

    static void getAllClassDescriptions(String currentSemester) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode){
    //Pre: Need the semester and course code
    //Post: Will return an arraylist of students scheduled using semester and course code
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Create Arraylist
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
                
        //Try catch to get student entries
        try{
            //Establish a connection using a prepare statement to get all scheduled students
            getScheduledStudentsID = connection.prepareStatement("SELECT semester, coursecode, studentid, status "
                                                                + "FROM app.schedule WHERE semester = ? AND "
                                                                + "coursecode = ? ORDER BY timestamp");
            
            //Set semester and coursecode in query
            getScheduledStudentsID.setString(1, semester);
            getScheduledStudentsID.setString(2, courseCode);
            
            //Execute query
            result = getScheduledStudentsID.executeQuery();
            
            //Get an arraylist of strings to store student id
            ArrayList<String> studentID = new ArrayList<String>();
            
            //Store ID's if scheduled
            while(result.next()){
                if("Scheduled".equals(result.getString("status")))
                    studentID.add(result.getString("studentid"));
            }
            

            //Iterate through the student ID's to get all the required items for StudentEntry
            for(String ID: studentID){
                //Establish a connection using a prepared statement to get all of the items for StudenEntry
                getScheduledStudentsByClass = connection.prepareStatement("SELECT studentid, firstname, lastname "
                                                                          + "FROM app.student WHERE studentid = ?");
                
                //Set ID in query
                getScheduledStudentsByClass.setString(1, ID);
                
                //Execute query
                result = getScheduledStudentsByClass.executeQuery();
                
                //Temp string to get results
                while(result.next()){
                    String tempID = result.getString("studentid");
                    String fName = result.getString("firstname");
                    String lName = result.getString("lastname");
                
                    //Create student using that information
                    StudentEntry student = new StudentEntry(tempID, fName, lName);
                
                    //Add into arraylist of studententry
                    students.add(student);
                
                }
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }    
        
        return students;
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
    //Pre: Need the semester and coursecode
    //Post: Will return an arraylist of students waitlisted using semester and coursecode
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Create Arraylist
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
                
        //Try catch to get student entries
        try{
            //Establish a connection using a prepare statement to get all scheduled students
            getWaitlistedStudentsID = connection.prepareStatement("SELECT semester, coursecode, studentid, status "
                                                                + "FROM app.schedule WHERE semester = ? AND "
                                                                + "coursecode = ? ORDER BY timestamp");
            
            //Set semester and coursecode in query
            getWaitlistedStudentsID.setString(1, semester);
            getWaitlistedStudentsID.setString(2, courseCode);
            
            //Execute query
            result = getWaitlistedStudentsID.executeQuery();
            
            //Get an arraylist of strings to store student id
            ArrayList<String> studentID = new ArrayList<String>();
            
            //Store ID's if waitlisted
            while(result.next()){
                if("Waitlisted".equals(result.getString("status")))
                    studentID.add(result.getString("studentid"));
            }
            

            //Iterate through the student ID's to get all the required items for StudentEntry
            for(String ID: studentID){
                //Establish a connection using a prepared statement to get all of the items for StudenEntry
                getWaitlistedStudentsByClass = connection.prepareStatement("SELECT studentid, firstname, lastname "
                                                                          + "FROM app.student WHERE studentid = ?");
                
                //Set ID in query
                getWaitlistedStudentsByClass.setString(1, ID);
                
                //Execute query
                result = getWaitlistedStudentsByClass.executeQuery();
                
                //Temp string to get results
                while(result.next()){
                    String tempID = result.getString("studentid");
                    String fName = result.getString("firstname");
                    String lName = result.getString("lastname");
                    
                    //Create student using that information
                    StudentEntry student = new StudentEntry(tempID, fName, lName);
                
                    //Add into arraylist of studententry
                    students.add(student);
                }
                

            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }    
        
        return students;
    }
}
