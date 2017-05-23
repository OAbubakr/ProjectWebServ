/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import bean.Course;
import bean.StudentSession;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author engra
 */
public class CourseDAO {

    private HibernateTemplate template;

    public CourseDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
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
}
