/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;

/**
 *
 * @author salma
 */
public class Instructor {
    
    int instuctorId;
    String instructorName;
    ArrayList<Course> courses = new ArrayList<>();

    public int getInstuctorId() {
        return instuctorId;
    }

    public void setInstuctorId(int instuctorId) {
        this.instuctorId = instuctorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
    
    
    
}
