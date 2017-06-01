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
import bean.TrackInstructor;
import static java.lang.Boolean.TRUE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javassist.bytecode.annotation.IntegerMemberValue;
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
                    Branch branch = new Branch();
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
                            Track track = new Track();
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
    
    public  ArrayList<Branch> getBranchesNames (){
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
                    branch.setBranchName((String) row[1]);
                    branch.setBranchArabicName((String) row[2]);
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
                for (Object[] row : courseList) {
                    Course course = new Course();
                    course.setCourseName((String) row[0]);
                    int courseID = (int) row[1];
                    course.setCourseId(courseID);
                    Query query2 = sn.createSQLQuery(
                            " { CALL GetInstructorIDByCourseID(:courseID) }")
                            .setParameter("courseID", courseID);

                    List<Object[]> IDList = query2.list();
                    ArrayList<Integer> indtructorID = new ArrayList<>();
                    HashMap<Integer, Integer> check = new HashMap<>();
                    ArrayList<TrackInstructor> trackInstructors = new ArrayList<>();
                    for (Object[] id : IDList) {
                        int i = (Integer) id[1];
                        indtructorID.add(i);
                        if (check.get(i) == null) {
                            check.put(i, 1);
                            Query query3 = sn.createSQLQuery(
                                    " { CALL GetEmpByID(:EmployeeID) }")
                                    .setParameter("EmployeeID", i);

                            List<Object[]> instructorsList = query3.list();

                            for (Object[] objects : instructorsList) {
                                 TrackInstructor trackInstructor = new TrackInstructor();
                                 trackInstructor.setInstructorID((int) objects[0]);
                                 trackInstructor.setInstructorName((String) objects[1]);
                                 trackInstructor.setInstructorImage((String) objects[13]);
                                 int positionID = (int) objects[16];
                                 Query query4 = sn.createSQLQuery(
                                    " { CALL GetPositionByID(:PositionID) }")
                                    .setParameter("PositionID", positionID);

                                List<Object[]> positions = query4.list();
                                for (Object[] position : positions) {
                                    trackInstructor.setInstructorPosition((String) position[1]);
                                }
                                 trackInstructors.add(trackInstructor);
                            }
                            course.setTrackInstructors(trackInstructors);
                        }

                    }
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
                for (Object[] row : courseList) {
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
