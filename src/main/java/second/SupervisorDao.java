/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.StudentDataByTrackID;
import bean.Supervisor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author admin
 */
public class SupervisorDao {
    
    private HibernateTemplate template;

    public SupervisorDao() {
    }
    
    
    
    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    
    public Supervisor getSupervisorByTrackId(final int id){
        
        
        
         return template.execute(new HibernateCallback<Supervisor>() {
            @Override
            public Supervisor doInHibernate(Session sn) throws HibernateException, SQLException {
                String idString = String.valueOf(id);
               Query query =sn.createSQLQuery("{ CALL GetSupervisorByTrackId(:PlatformIntakeId) }")
                       .setParameter("PlatformIntakeId",idString);
               
           
               
           List<Object[]> list = query.list();
           
                  Supervisor supervisor = new Supervisor();      
                  supervisor.setId((Integer)list.get(0)[0]);
                  supervisor.setName((String)list.get(0)[1]);
                 
                
                return supervisor;
            }
        });
        
        
      
        
    } 
    
    
    
    
    
    
}
