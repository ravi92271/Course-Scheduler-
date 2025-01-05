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
import java.sql.Timestamp;

public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addSchedule;
    private static PreparedStatement getScheduleByStudentList;
    private static PreparedStatement getScheduledStudentCountList;
    private static PreparedStatement getWaitlistedStudentList;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet result;
    
    public static void addScheduleEntry(ScheduleEntry schedule){
    //Pre: Need the schedule to add into DB
    //Post: Will add schedule to DB
    
        //Establish connection to DB
        connection = DBConnection.getConnection();
        
        //Try catch for adding schedule
        try{
            //Have a prepared statement to go into the schedule table and insert a value
            addSchedule = connection.prepareStatement("INSERT into app.schedule (semester, coursecode, studentid, status, timestamp) "
                                                     + "values (?, ?, ?, ?, ?)");
            
            //Add an object to class table
            addSchedule.setString(1, schedule.getSemester());
            addSchedule.setString(2, schedule.getCourseCode());
            addSchedule.setString(3, schedule.getStudentID());
            addSchedule.setString(4, schedule.getStatus());
            addSchedule.setTimestamp(5, schedule.getTimeStamp());
            
            //Execute the update
            addSchedule.executeUpdate();
        
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
    //Pre: Will need semester and student ID
    //Post: Will return an array list of schedules
    
        //Establish a connection to DB
        connection = DBConnection.getConnection();
        
        //Create arraylist
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        
        //Try catch to get all schedules
        try{
            //Establish a connection using a prepared statement and extract schdules
            getScheduleByStudentList = connection.prepareStatement("SELECT semester, coursecode, studentid, status, timestamp "
                                                                    + "FROM app.schedule WHERE semester = ? AND "
                                                                    + "studentid = ? ORDER BY studentid");
        
            //Set the semester and studentID parameter in the query
            getScheduleByStudentList.setObject(1, semester);
            getScheduleByStudentList.setObject(2, studentID);
            
            //Store into resultset
            result = getScheduleByStudentList.executeQuery();
            
            //Iterate through result to add onto schedule
            while(result.next()){
                //Establish temp variables
                String tempSem = result.getString("semester");
                String tempCourseCode = result.getString("coursecode");
                String tempStudentID = result.getString("studentid");
                String tempStatus = result.getString("status");
                Timestamp tempStamp = result.getTimestamp("timestamp");
                
                ScheduleEntry tempSchedule = new ScheduleEntry(tempSem, tempCourseCode, tempStudentID, tempStatus, tempStamp);
                schedule.add(tempSchedule);
            }
        
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    
        return schedule;
    } 
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode){
    //Pre: Need the semester and student id
    //Post: Will return how much students are scheduled in this class
    
        //Enstablish a connection
        connection = DBConnection.getConnection();
        
        //Create an int to return
        int studentCount = 0;
        
        //Try catch to get student count
        try{
            //Establish a connection using a prepared statement and extract schdules
            getScheduledStudentCountList = connection.prepareStatement("SELECT count(studentid) AS count FROM app.schedule WHERE "
                                                                        + "semester = ? AND coursecode = ?");
            
            //Set semester and studentID 
            getScheduledStudentCountList.setString(1, currentSemester);
            getScheduledStudentCountList.setString(2, courseCode);
            
            //Execute query
            result = getScheduledStudentCountList.executeQuery();
            
            //Get result
            while(result.next()){
                studentCount = result.getInt("count");
            }
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        
        return studentCount;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
    //Pre: Need the semester and courseode
    //Post: Will return all the waitlisted students by class
        
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Create an arraylist
        ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<ScheduleEntry>();
        
        //Try catch to get all waitlisted students by class
        try{
            //Establish a connection using a prepare statement
            getWaitlistedStudentList = connection.prepareStatement("SELECT semester, coursecode, studentid, status, "
                                                                   + "timestamp FROM app.schedule WHERE semester = ? "
                                                                   + "AND coursecode = ? AND status = 'Waitlisted' "
                                                                   + "ORDER BY timestamp ");
            
            //Set semester and coursecode
            getWaitlistedStudentList.setString(1, semester);
            getWaitlistedStudentList.setString(2, courseCode);
            
            //Execute query
            result = getWaitlistedStudentList.executeQuery();
            
            while(result.next()){
                //Get all information
                String sem = result.getString("semester");
                String code = result.getString("coursecode");
                String studentid = result.getString("studentid");
                String status = result.getString("status");
                Timestamp t = result.getTimestamp("timestamp");
                
                //Store in temp variable
                ScheduleEntry schedule = new ScheduleEntry(sem, code, studentid, status, t);
                
                //Add in arraylist
                waitlistedStudents.add(schedule);
            
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    
        return waitlistedStudents;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
    //Pre: Need the semester, studentid, and coursecode
    //Post: Will drop the course 
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Try catch to drop course
        try{
            //Establish a connection using prepare statement
            dropStudentScheduleByCourse = connection.prepareStatement("DELETE FROM app.schedule WHERE semester = ? "
                                                                    + "AND studentid = ? AND coursecode = ?");
        
            //Set semester, student id, and course code
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            
            //Execute update
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    
    
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
    //Pre: Need semester and coursecode
    //Post: Will drop the schedule by the course code
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Try catch to drop the schedule
        try{
            //Establish a connection to the DB using semseter and coursecode
            dropScheduleByCourse = connection.prepareStatement("DELETE FROM app.schedule WHERE semester = ? "
                                                               + "AND coursecode = ?");
            
            //Set semester and coursecode
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            
            //Execute update
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(ScheduleEntry entry){
    //Pre: Need the ScheduleEntry
    //Post: Will update the schedule entry
    
        //Establish a connection
        connection = DBConnection.getConnection();
        
        //Try catch to update schedule
        try{
            //Establish a connection using a schedule entry
            updateScheduleEntry = connection.prepareStatement("UPDATE app.schedule SET status = 'Scheduled' "
                                                              + "WHERE semester = ? AND coursecode = ? AND "
                                                              + "studentid = ? AND timestamp = ?");
            
            //Set everything needed in schedule entry
            updateScheduleEntry.setString(1, entry.getSemester());
            updateScheduleEntry.setString(2, entry.getCourseCode());
            updateScheduleEntry.setString(3, entry.getStudentID());
            updateScheduleEntry.setTimestamp(4, entry.getTimeStamp());
            
            //Execute update
            updateScheduleEntry.executeUpdate();
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    
    }
}
