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
                EmployeeHours employeeHours = new EmployeeHours();
                System.out.println("size is "+ list.size());
                employeeHours.setWorkingDays((int)list.get(0)[0]);
                employeeHours.setAbsenceDays((int)list.get(0)[1]);
                employeeHours.setLateDays((int)list.get(0)[2]);
                employeeHours.setAttendDays((int)list.get(0)[3]);
                employeeHours.setAttendHours((int)list.get(0)[4]);
                employeeHours.setMissionHours((int)list.get(0)[5]);
                employeeHours.setVacationHours((int)list.get(0)[6]);
                employeeHours.setPermissionHours((int)list.get(0)[7]);
                
                return employeeHours;
            }
        });
    }
}
