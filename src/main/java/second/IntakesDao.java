/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.Course;
import bean.GraduateBasicData;
import bean.ProgramIntake;
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
public class IntakesDao {
     private HibernateTemplate template;

    public IntakesDao() {
    }

    
    
    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    
     public ArrayList<ProgramIntake> getAllProgramIntake(){
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                
                Query query =sn.createSQLQuery("{ CALL getProgramIntakes }");
                List<Object[]> programs = query.list();
                ArrayList<ProgramIntake> programIntakes = new ArrayList<>();
                for(Object[] row : programs){
                    
                    ProgramIntake programIntake = new ProgramIntake();
                    programIntake.setIntakeId((int) row[0]);
                    programIntake.setProgramId((int) row[1]);
                    programIntake.setProgramIntakeId((int) row[2]);
                    programIntakes.add(programIntake);
                }
                return programIntakes;
            }
        });
        
        
    }
}
