/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.InstructorEvaluation;
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
 * @author Rana Gamal
 */
public class InstructorEvaluationDAO {
    private HibernateTemplate template;

    public InstructorEvaluationDAO() {
    }
    
    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    public ArrayList<InstructorEvaluation> getInstEvaluation(final int instId) {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
            
                Query query = sn.createSQLQuery(" { CALL getInstEval(:instId) }")
                        .setParameter("instId", String.valueOf(instId));
                
                List<Object[]> list = query.list();
                ArrayList<InstructorEvaluation> instructorEvaluation = new ArrayList<>();

                for (Object[] row : list) {
                    
                    InstructorEvaluation iv = new InstructorEvaluation();
                    
                    iv.setGeneralEvaluation((String) row[0]);
                    iv.setEval((String) row[2]);
                    iv.setInstId((int) row[3]);
                    iv.setCourseId((int) row[4]);
                    iv.setStudentId((int) row[6]);
                    iv.setTypeId((int) row[7]);
                    iv.setGroupId((int) row[8]);
                    iv.setComment((String) row[9]);
                    
                    instructorEvaluation.add(iv);
                }
                return instructorEvaluation;
            }
        });
    }  
    
    
}
