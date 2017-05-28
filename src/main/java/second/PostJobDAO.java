/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Rana Gamal
 */
public class PostJobDAO {
    
    private HibernateTemplate template;

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    public PostJobDAO(){
   
    }
    
    public String postJob( final int companyId, final String jobCode, final String jobTitle,
            final String jobDesc, final String experience, final String closingDate,
            final String sendTo, final int jobNoNeed, final int subTrackId, final String jobDate ){
        
        final String sql = "{ CALL PostJop(:companyId, :jobCode, :jobTitle, :jobDesc, :experience,"
                + ":closingDate, :sendTo, :jobNoNeed, :subTrackId, :jobDate) }";
        
        return (String) template.execute(new HibernateCallback<Object>(){
            @Override
            public Object doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(sql)
                        .setParameter("companyId", String.valueOf(companyId))
                        .setParameter("jobCode",jobCode)
                        .setParameter("jobTitle", jobTitle)
                        .setParameter("jobDesc", jobDesc)
                        .setParameter("experience", experience)
                        .setParameter("closingDate", closingDate)
                        .setParameter("sendTo", sendTo)
                        .setParameter("jobNoNeed", String.valueOf(jobNoNeed))
                        .setParameter("subTrackId", String.valueOf(subTrackId))
                        .setParameter("jobDate", jobDate);
                
                query.executeUpdate();
                return "Insertion Done";
            }  
        });
        
    }
}
