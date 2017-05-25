/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.Course;
import bean.EmployeeHours;
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
public class EmpHoursDAO {

    private HibernateTemplate template;

    public EmpHoursDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public EmployeeHours getEmployeeHours(final int id, final String start, final String end) {
        return template.execute(new HibernateCallback<EmployeeHours>() {
            @Override
            public EmployeeHours doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(
                        " { CALL GetEmpReport(:EmpID,:StartDate,:EndDate) }")
                        .setParameter("EmpID", id)
                        .setParameter("StartDate", start)
                        .setParameter("EndDate", end);
                System.out.println(query.list().toString());
                List<Object[]> list = query.list();
                ArrayList<EmployeeHours> employeeHourses = new ArrayList<>();
                EmployeeHours emp = new EmployeeHours();
                emp.setWorkingDays((int) list.get(0)[0]);
                emp.setAbsenceDays((int) list.get(0)[1]);
                emp.setLateDays((int) list.get(0)[2]);
                emp.setAttendDays((int) list.get(0)[3]);
                emp.setAttendHours((int) list.get(0)[4]);
                emp.setMissionHours((int) list.get(0)[5]);
                emp.setVacationHours((int) list.get(0)[6]);
                emp.setPermissionHours((int) list.get(0)[7]);

//                for(Object[] row:list){
//                    EmployeeHours empl_hours = new EmployeeHours();
//                    empl_hours.setWorkingDays((int) row[0]);
//                    empl_hours.setAbsenceDays((int) row[1]);
//                    empl_hours.setLateDays((int) row[2]);
//                    empl_hours.setAttendDays((int) row[3]);
//                    empl_hours.setAttendHours((int) row[4]);
//                    empl_hours.setMissionHours((int) row[5]);
//                    empl_hours.setVacationHours((int) row[6]);
//                    empl_hours.setPermissionHours((int) row[7]);
//                    
//                    employeeHourses.add(empl_hours);
//                }
//                
                return emp;
            }
        });
    }
}
