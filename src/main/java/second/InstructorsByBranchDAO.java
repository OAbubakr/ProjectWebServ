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

    public List<Instructor> getInstructorsByBranch(final int branchIdArg, final int excludeId) {

        return template.execute(new HibernateCallback<ArrayList<Instructor>>() {
            @Override
            public ArrayList<Instructor> doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(" { CALL GetAllInstructorsWithBranchName() }");
                List<Object[]> data = query.list();
                ArrayList<Instructor> instructors = new ArrayList<>();

                int id;
                int instructorId;
                for (Object[] row : data) {
                    //get all instructors
                    instructorId = (int)row[0];
                    if(excludeId != instructorId){                        
                        instructors.add(fillInstructor(row));
                    }
                    

                }

                return instructors;
            }

        });
    }

    private Instructor fillInstructor(Object[] row) {

        Instructor instructor = new Instructor();
        instructor.setInstuctorId((int) row[0]);
        instructor.setInstructorName((String) row[1]);
        instructor.setBranchId((int) row[2]);
        instructor.setImagePath((String) row[13]);
        instructor.setBranchName((String) row[56]);
        instructor.setArabicBranchName((String) row[57]);
        
        return instructor;
    }

}
