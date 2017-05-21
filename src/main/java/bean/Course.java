package bean;
// Generated May 20, 2017 7:06:35 PM by Hibernate Tools 4.3.1


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Course generated by hbm2java
 */
public class Course  implements java.io.Serializable {


     private int courseId;
     private CourseCategory courseCategory;
     private Serializable courseName;
     private Set courseManuals = new HashSet(0);

    public Course() {
    }

	
    public Course(int courseId) {
        this.courseId = courseId;
    }
    public Course(int courseId, CourseCategory courseCategory, Serializable courseName, Set courseManuals) {
       this.courseId = courseId;
       this.courseCategory = courseCategory;
       this.courseName = courseName;
       this.courseManuals = courseManuals;
    }
   
    public int getCourseId() {
        return this.courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public CourseCategory getCourseCategory() {
        return this.courseCategory;
    }
    
    public void setCourseCategory(CourseCategory courseCategory) {
        this.courseCategory = courseCategory;
    }
    public Serializable getCourseName() {
        return this.courseName;
    }
    
    public void setCourseName(Serializable courseName) {
        this.courseName = courseName;
    }
    public Set getCourseManuals() {
        return this.courseManuals;
    }
    
    public void setCourseManuals(Set courseManuals) {
        this.courseManuals = courseManuals;
    }




}


