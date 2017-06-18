/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.StudentSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    public ArrayList<StudentSession> getStudentSchedule(final int studentId) {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                Calendar calendar = GregorianCalendar.getInstance();
                //setting the first day of the week to be saturday
                calendar.setFirstDayOfWeek(Calendar.SATURDAY);

                //setting the calendar to the first saturday of this week
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String fromDate = dateFormat.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_WEEK, 13);
                String toDate = dateFormat.format(calendar.getTime());
                
                System.out.println(fromDate);
                System.out.println(toDate);
                
                //to put the parameters of the fromDate and toDate

                Query query = sn.createSQLQuery(
                        " { CALL GetScheduleByStudentID(:studentId, :fromDate, :toDate) }")
//                        .setParameter("studentId", "5699")
                        .setParameter("studentId", String.valueOf(studentId))
                        .setParameter("fromDate", "2015-11-21")
                        .setParameter("toDate", "2015-12-4");
                List<Object[]> list = query.list();
                ArrayList<StudentSession> sessions = new ArrayList<>();

                for (Object[] row : list) {
                    StudentSession studentSession = new StudentSession();
                    studentSession.setCourseName((String) row[3]);

                    Timestamp ts = (Timestamp) row[4];
                    studentSession.setSessionDate(ts.getTime());
                    studentSession.setSessionTime((String) row[1]);
                    studentSession.setTypeId((int) row[2]);
                    studentSession.setWeekNumber((int) row[6]);
                    studentSession.setSessionId((int) row[9]);
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
