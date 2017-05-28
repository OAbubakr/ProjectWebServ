/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.EmployeeHours;
import bean.Instructor;
import bean.StudentSession;
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
 * @author home
 */
public class InstructorsByBranchDAO {

    private HibernateTemplate template;

    public InstructorsByBranchDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public List<Instructor> getInstructorsByBranch(final int branchIdArg) {

        template.execute(new HibernateCallback<EmployeeHours>() {
            @Override
            public EmployeeHours doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(" { CALL GetAllInstructor() }");
                List<Object[]> data = query.list();
                ArrayList<Instructor> instructors = new ArrayList<>();

                int id;
                for (Object[] row : data) {
                    id = (int) row[2];
                    if (id == branchIdArg) {
                        Instructor instructor = new Instructor();
                        instructor.setInstuctorId((int) row[0]);
                        instructor.setInstructorName((String) row[1]);
                        instructor.setBranchId(id);
                        instructors.add(instructor);
                    }
                }

                for (Instructor instructor : instructors) {
                    System.out.println(instructor.getInstuctorId() + " " + instructor.getInstructorName() + "  " + instructor.getBranchId());
                }

                return null;
            }

        });
        return null;
    }

}
