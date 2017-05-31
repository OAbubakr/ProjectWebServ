/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.InstructorSession;
import bean.StudentSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Sandra
 */
public class InstructorScheduleDao {

    private HibernateTemplate template;

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public InstructorScheduleDao() {
    }
        

    public ArrayList<InstructorSession> getInstructorSchedule(final int instructorId) {
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
                        " { CALL GetScheduleByInstructorID(:instructorId, :fromDate, :toDate) }")
                        .setParameter("instructorId", String.valueOf(instructorId))
                        .setParameter("fromDate", "2014-10-20")
                        .setParameter("toDate", "2014-11-2");
                List<Object[]> list = query.list();
                ArrayList<InstructorSession> sessions = new ArrayList<>();

                for (Object[] row : list) {
                    InstructorSession instructorSession = new InstructorSession();
                    instructorSession.setSessionId((int) row[8]);
                    instructorSession.setCourseName((String) row[2]);
                    instructorSession.setWeekNumber((int) row[5]);
                    instructorSession.setTrackName((String) row[13]);
                    instructorSession.setRoomName((String) row[11]);
                    instructorSession.setSessionPercentage((String) row[15]);
                    instructorSession.setBranchName((String) row[16]);
                    Timestamp timestamp = (Timestamp) row[3];
                    instructorSession.setSessionDate(timestamp.getTime());
                    
                    

                    sessions.add(instructorSession);
                }
                return sessions;
            }

        });
    }
}

