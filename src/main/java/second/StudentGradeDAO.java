/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.Course;
import bean.StudentGrade;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author engra
 */
public class StudentGradeDAO {

    private HibernateTemplate template;

    public StudentGradeDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }
    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    public ArrayList<StudentGrade> getAllStudentGrade(final int id) {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(
                        " { CALL GetAllStudentGrade(:StudentID) }")
                        .setParameter("StudentID", id);

                List<Object[]> gradelist = query.list();
                ArrayList<StudentGrade> studentGrades = new ArrayList<>();
                for(Object[] row:gradelist){
                    StudentGrade studentGrade = new StudentGrade();
                    studentGrade.setCourseID((int) row[1]);
                    studentGrade.setCourseName((String) row[2]);
                    studentGrade.setGradeName((String) row[3]);
                    studentGrade.setFinishedCourse((int) row[4]);
                    studentGrade.setEvaluatedCourse((int) row[5]);
                    studentGrade.setCourseManualID((int) row[6]);
                    studentGrade.setCourseDescription((String) row[7]);
                    
                    studentGrades.add(studentGrade);
                }
                return studentGrades;
            }
        });
    }
}
