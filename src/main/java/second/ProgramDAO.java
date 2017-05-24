/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.Branch;
import bean.Course;

import bean.StudentSession;
import bean.Track;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author salma
 */
public class ProgramDAO {

     private HibernateTemplate template;
    public ProgramDAO() {
    }
    
    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    
//    public  List<Object[]> getTracks (){
//    
//    
//     List<Object[]> d = template.findByCriteria(DetachedCriteria.forClass(TrackManual.class, "tm").createAlias("platfromIntake", "pfi")
//             .createAlias("pfi.subTrack", "subt").createAlias("courseManual", "cm").createAlias("cm.course", "c")
//             .setProjection(Projections.projectionList().add(Projections.property("subt.subtrackName")).add(Projections.property("c.courseName"))));
//
//    System.out.print(d);
//    return d;
//    }
    
    
    
    
  public  ArrayList<Branch> getBranches (){
    
    
       return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(
                        " { CALL GetAllBranch }");
                
                System.out.println("in getbraches method in programDao");
                
                List<Object[]> list = query.list();
                ArrayList<Branch> branches = new ArrayList<>();

                for (Object[] row : list) {
                    Branch branch =new Branch();
                    branch.setBranchId((int) row[0]);
                    String branchName = (String) row[1];
                    branch.setBranchName((String) row[1]);
                    branch.setBranchArabicName((String) row[2]);
                    Query secondQuery = sn.createSQLQuery(
                        " { CALL GetAllsubTrack }");
                    List<Object[]> secondList = secondQuery.list();
                    ArrayList<Track> tracks = new ArrayList<>();
                     for (Object[] item : secondList) {
                        
                        String trackBranchName = (String) item[3];
                         if (branchName.equals(trackBranchName)) {
                             Track track =new Track();
                             track.setTrackName((String) item[0]);
                             track.setPlatformName((String) item[1]);
                             track.setTrackId((int) item[2]);
                             int platformIntake = (int) item[4];
                             track.setPlatformIntakeId((int) item[4]);
                             
//                             Query thirdQuery = sn.createSQLQuery(
//                                " { CALL GetCourseByTrackId(:PlatformIntakeID) }")
//                                .setParameter("PlatformIntakeID", platformIntake);
//
//                            List<Object[]> courseList = thirdQuery.list();
//                            ArrayList<Course> courses = new ArrayList<>();
//                            
//                            for(Object[] roww:courseList){
//                                Course course = new Course();
//                                course.setCourseName((String) roww[0]);
//                                course.setCourseId((int) roww[1]);
//
//                                courses.add(course);
//                            }
//                            
//                            track.setCourses(courses);
                             
                             
                             tracks.add(track);
                         }
                         
                     }
                    
                    branch.setTracks(tracks);
                    branches.add(branch);
                    

                    
                }
                return branches;
            }

        });
    }  
    
    public ArrayList<Course> getCourseByTrackId(final int id) {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(
                        " { CALL GetCourseByTrackId(:PlatformIntakeID) }")
                        .setParameter("PlatformIntakeID", id);

                List<Object[]> courseList = query.list();
                ArrayList<Course> courses = new ArrayList<>();
                for(Object[] row:courseList){
                    Course course = new Course();
                    course.setCourseName((String) row[0]);
                    course.setCourseId((int) row[1]);
                    
                    courses.add(course);
                }
                return courses;
            }
        });
    }
    
    
    
    
    public ArrayList<Course> GetAllInstructorsCourseByEmpId(final int id) {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(
                        " { CALL GetAllInstructorsCourseByEmpId(:EmployeeID) }")
                        .setParameter("EmployeeID", id);

                List<Object[]> courseList = query.list();
                ArrayList<Course> courses = new ArrayList<>();
                for(Object[] row:courseList){
                    Course course = new Course();
                    course.setCourseName((String) row[0]);
                    course.setCourseId((int) row[3]);
                    
                    courses.add(course);
                }
                return courses;
            }
        });
    }
}
