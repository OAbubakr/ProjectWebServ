/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.StudentSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Convert;
import javax.persistence.TemporalType;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Sandra
 */
public class StudentScheduleDao {

    private HibernateTemplate template;

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public StudentScheduleDao() {
    }

    public ArrayList<StudentSession> getStudentSchedule() {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(
                        " { CALL GetScheduleByStudentID(:studentId, :fromDate, :toDate) }")
                        .setParameter("studentId", "5699")
                        .setParameter("fromDate", "2014-10-20")
                        .setParameter("toDate", "2014-10-30");
                List<Object[]> list = query.list();
                ArrayList<StudentSession> sessions = new ArrayList<>();

                for (Object[] row : list) {
                    StudentSession studentSession = new StudentSession();
                    studentSession.setSessionTime((String) row[1]);
                    studentSession.setCourseName((String) row[3]);
                    studentSession.setWeekNumber((int) row[6]);
                    studentSession.setRoomName((String) row[11]);
                    studentSession.setInstructorName((String) row[12]);
                    studentSession.setSessionPercentage((String) row[14]);

                    sessions.add(studentSession);
                }
                return sessions;
            }

        });
    }

}
