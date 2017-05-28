/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author engra
 */
public class StudentGrade {
    private String courseName;
    private int courseID;
    private String gradeName;
    private int finishedCourse;
    private int evaluatedCourse;
    private int courseManualID;
    private String courseDescription;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getFinishedCourse() {
        return finishedCourse;
    }

    public void setFinishedCourse(int finishedCourse) {
        this.finishedCourse = finishedCourse;
    }

    public int getEvaluatedCourse() {
        return evaluatedCourse;
    }

    public void setEvaluatedCourse(int evaluatedCourse) {
        this.evaluatedCourse = evaluatedCourse;
    }

    public int getCourseManualID() {
        return courseManualID;
    }

    public void setCourseManualID(int courseManualID) {
        this.courseManualID = courseManualID;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
    
}
