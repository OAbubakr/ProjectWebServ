/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.JobOpportunity;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import notifications.FCMNotification;
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
    
//    public String postJob( final int companyId, final String jobCode, final String jobTitle,
//            final String jobDesc, final String experience, final String closingDate,
//            final String sendTo, final int jobNoNeed, final int subTrackId, final String jobDate ){
//        
//        final String sql = "{ CALL PostJop(:companyId, :jobCode, :jobTitle, :jobDesc, :experience,"
//                + ":closingDate, :sendTo, :jobNoNeed, :subTrackId, :jobDate) }";
//        
//        return (String) template.execute(new HibernateCallback<Object>(){
//            @Override
//            public Object doInHibernate(Session sn) throws HibernateException, SQLException {
//                Query query = sn.createSQLQuery(sql)
//                        .setParameter("companyId", String.valueOf(companyId))
//                        .setParameter("jobCode",jobCode)
//                        .setParameter("jobTitle", jobTitle)
//                        .setParameter("jobDesc", jobDesc)
//                        .setParameter("experience", experience)
//                        .setParameter("closingDate", closingDate)
//                        .setParameter("sendTo", sendTo)
//                        .setParameter("jobNoNeed", String.valueOf(jobNoNeed))
//                        .setParameter("subTrackId", String.valueOf(subTrackId))
//                        .setParameter("jobDate", jobDate);
//                
//                query.executeUpdate();
//                return "Insertion Done";
//            }  
//        });
//        
//    }
    
    public String postJob( final JobOpportunity jobOpportunity ){
        
        final String sql = "{ CALL PostJop(:companyId, :jobCode, :jobTitle, :jobDesc, :experience,"
                + ":closingDate, :sendTo, :jobNoNeed, :subTrackId, :jobDate) }";
        
        template.execute(new HibernateCallback<Object>(){
            @Override
            public Object doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(sql)
                        .setParameter("companyId", String.valueOf(jobOpportunity.getCompanyId()))
                        .setParameter("jobCode",jobOpportunity.getJobCode())
                        .setParameter("jobTitle", jobOpportunity.getJobTitle())
                        .setParameter("jobDesc", jobOpportunity.getJobDesc())
                        .setParameter("experience", jobOpportunity.getExperience())
                        .setParameter("closingDate", jobOpportunity.getClosingDate())
                        .setParameter("sendTo", jobOpportunity.getSendTo())
                        .setParameter("jobNoNeed", String.valueOf(jobOpportunity.getJobNoNeed()))
                        .setParameter("subTrackId", String.valueOf(jobOpportunity.getSubTrackId()))
                        .setParameter("jobDate", jobOpportunity.getJobDate());
                
                query.executeUpdate();
                return null;
            }  
        });
        
          try {
            FCMNotification.sendNotification(FCMNotification.JOB_POST, jobOpportunity.getCompanyName()+" has posted a job.",createJobString(jobOpportunity), "jobPosts");
        } catch (Exception ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return "Insertion Done";
    }
    
    private String createJobString(JobOpportunity jobOpportunity){
    
    
    return "Job Title: "+jobOpportunity.getJobTitle();
//            + "\nJob Description: "+jobOpportunity.getJobDesc()
//            + "\n Years of Experience: " +jobOpportunity.getExperience()
//            + "\n Closing Date: " + jobOpportunity.getClosingDate()
//            + "\n Number needed: " + jobOpportunity.getJobNoNeed()
//            + "\n Send CV to: "+ jobOpportunity.getSendTo();
    
    }
}
