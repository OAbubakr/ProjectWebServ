/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

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
import bean.StudentSession;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author HP
 */
public class TrackScheduleDao {
    
     private HibernateTemplate template;

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public TrackScheduleDao() {
    }

    public ArrayList<StudentSession> getTrackSchedule(final int TrackId) {
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
                        " { CALL GetSchedule(:PlatformIntakeID, :fromDate, :toDate) }")
//                        .setParameter("studentId", "5699")
                        .setParameter("PlatformIntakeID", String.valueOf(TrackId))
                        .setParameter("fromDate", "2014-10-20")
                        .setParameter("toDate", "2014-11-3");
                List<Object[]> list = query.list();
                ArrayList<StudentSession> sessions = new ArrayList<>();

                for (Object[] row : list) {
                    StudentSession studentSession = new StudentSession();
                    studentSession.setCourseName((String) row[3]);
                    System.out.println("course name "+(String) row[3]);

                    Timestamp ts = (Timestamp) row[4];
                    studentSession.setSessionDate(ts.getTime());
                    studentSession.setSessionTime((String) row[1]);
                    System.out.println("session time "+(String) row[1]);
                    studentSession.setTypeId((int) row[2]);
                    System.out.println(" type id"+(int) row[2]);
                    studentSession.setWeekNumber((int) row[6]);
                    System.out.println("week number "+(int) row[6]);
                    studentSession.setSessionId((int) row[9]);
                    System.out.println("session id  "+(int) row[9]);
                    studentSession.setRoomName((String) row[11]);
                    System.out.println(" rooom"+(String) row[11]);
                    studentSession.setInstructorName((String) row[12]);
                    System.out.println("instructor name "+(String) row[12]);
                    
                    studentSession.setSessionPercentage((String) row[15]);
                    System.out.println("session percentage "+(String) row[15]);

                    sessions.add(studentSession);
                }
                return sessions;
            }

        });
    }
    
}
