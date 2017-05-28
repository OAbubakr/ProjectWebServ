/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;


import bean.CompanyProfile;
import bean.JobVacancy;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author salma
 */
public class CompanyDAO {
    private HibernateTemplate template;

    public CompanyDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    
    public CompanyProfile getCompanyProfile(final int id) {
        return (CompanyProfile) template.execute(new HibernateCallback() {
            @Override
            public CompanyProfile doInHibernate(Session sn) throws HibernateException, SQLException {
                CompanyProfile companyProfile = null;
                Query query = sn.createSQLQuery(
                        " { CALL GetCompanyProfileByCompanyID(:CompanyID) }")
                        .setParameter("CompanyID", id);
                List<Object[]> companyList = query.list();
                for (Object[] cObject : companyList) {
                               
                companyProfile = new CompanyProfile();
                companyProfile.setCompanyID(id);
                companyProfile.setCompanyName((String) cObject[1]);
                companyProfile.setCompanyNoOfEmp(Integer.parseInt((String) cObject[2]));
                companyProfile.setCompanyAreaKnowledge((String) cObject[3]);
                companyProfile.setCompanyWebSite((String) cObject[4]);
                companyProfile.setCompanyAddress((String) cObject[5]);
                companyProfile.setCompanyPhone((String) cObject[6]);
                companyProfile.setCompanyMobile((String) cObject[7]);
                companyProfile.setCompanyEmail((String) cObject[8]);
                companyProfile.setCompanyLogoPath((String) cObject[9]);
                companyProfile.setCompanyProfilePath((String) cObject[10]);
                companyProfile.setCompanyUserName((String) cObject[13]);
                companyProfile.setCompanyPassWord((String) cObject[14]); 
                }
                
                return companyProfile;
            }
        });
           
    }
    
    
    public ArrayList<JobVacancy> getAllJobs() {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(
                        " { CALL GetAllJopOpportunitie }");
                     
                System.out.println("jobbbbbbb mathod");
                List<Object[]> jobsList = query.list();
                ArrayList<JobVacancy> jobs = new ArrayList<>();
                for(Object[] row:jobsList){
                    JobVacancy job = new JobVacancy();
                    System.out.println("jobbbbbbb");
                    job.setJobID((int) row[0]);
                    int companyID=(int) row[1];
                    job.setCompanyID(companyID);
                    job.setJobCode((String) row[2]);
                    job.setJobTitle((String) row[3]);
                    job.setJobDesc((String) row[4]);
                    job.setJobYearExperience((int) row[5]);
                    SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
                    Date d1 = null;
                    Date d2 = null;
                    try {
                        d1 = f.parse((String) row[6]);
                        d2 = f.parse((String) row[10]);
                    } catch (ParseException ex) {
                        Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    job.setJobClosingDate(d1.getTime()); 
                    job.setJobDate(d2.getTime());
                    job.setJobCVTo((String) row[7]);
                    job.setJobNoNeed((int) row[8]);
                    job.setSubTrackID((int) row[9]);
                    
                    Query query2 = sn.createSQLQuery(
                        " { CALL GetCompanyProfileByCompanyID(:CompanyID) }")
                        .setParameter("CompanyID", companyID);
                    List<Object[]> companyList = query2.list();
                for (Object[] cObject : companyList) {
                    job.setCompanyName((String) cObject[1]);
                }
               
                    jobs.add(job);
            }
                return jobs;
            }
        });
}
}





