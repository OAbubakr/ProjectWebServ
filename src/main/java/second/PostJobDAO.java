/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    
    public int InsertNewJop( final int companyId, final String jopCode, final String jopTitle,
            final String jopDesc, final String experience, final String closingDate,
            final String sendTo, final int jopNoNeed, final int subTrackId, final String jopDate ){
        
        final String sql = "{ CALL PostJop(:companyId, :jopCode, :jopTitle, :jopDesc, :experience,"
                + ":closingDate, :sendTo, :jopNoNeed, :subTrackId, :jopDate) }";
        
        return (int) template.execute(new HibernateCallback<Object>(){
            @Override
            public Object doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(sql)
                        .setParameter("companyId", String.valueOf(companyId))
                        .setParameter("jopCode",jopCode)
                        .setParameter("jopTitle", jopTitle)
                        .setParameter("jopDesc", jopDesc)
                        .setParameter("experience", experience)
                        .setParameter("closingDate", closingDate)
                        .setParameter("sendTo", sendTo)
                        .setParameter("jopNoNeed", String.valueOf(jopNoNeed))
                        .setParameter("subTrackId", String.valueOf(subTrackId))
                        .setParameter("jopDate", jopDate);
                
                int check = query.executeUpdate();
                return check;
            }  
        });
        
    }
}
