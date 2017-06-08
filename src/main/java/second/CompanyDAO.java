/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;


import bean.CompanyProfile;
import bean.JobVacancy;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

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
                if(cObject[1]!=null) companyProfile.setCompanyName((String) cObject[1]);
                if(cObject[2]!=null) companyProfile.setCompanyNoOfEmp(Integer.parseInt((String) cObject[2]));
                if(cObject[3]!=null) companyProfile.setCompanyAreaKnowledge((String) cObject[3]);
                if(cObject[4]!=null) companyProfile.setCompanyWebSite((String) cObject[4]);
                if(cObject[5]!=null) companyProfile.setCompanyAddress((String) cObject[5]);
                if(cObject[6]!=null)companyProfile.setCompanyPhone((String) cObject[6]);
                if(cObject[7]!=null)companyProfile.setCompanyMobile((String) cObject[7]);
                if(cObject[8]!=null)companyProfile.setCompanyEmail((String) cObject[8]);
                if(cObject[9]!=null)companyProfile.setCompanyLogoPath((String) cObject[9]);
                if(cObject[10]!=null) companyProfile.setCompanyProfilePath((String) cObject[10]);

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
                   job.setJobYearExperience((String) row[5]);
                    SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
                    Date date1 = (Date) row[6];
                    Date date2 = (Date) row[10];
                    long d1 = date1.getTime();
                    long d2 = date2.getTime();
                    job.setJobClosingDate(d1); 
                    job.setJobDate(d2);
                    job.setJobCVTo((String) row[7]);
                    job.setJobNoNeed((int) row[8]);
                   // job.setSubTrackID((int) row[9]);
                    
                    Query query2 = sn.createSQLQuery(
                        " { CALL GetCompanyProfileByCompanyID(:CompanyID) }")
                        .setParameter("CompanyID", companyID);
                    List<Object[]> companyList = query2.list();
                for (Object[] cObject : companyList) {
                    job.setCompanyName((String) cObject[1]);
                    job.setCompanyLogoPath((String)cObject[9]);
                }
               
                    jobs.add(job);
            }
                return jobs;
            }
        });
}

    public ArrayList<JobVacancy> getAllCompanies() {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(
                        " { CALL getAllCompanies }");
                     
                List<Object[]> companiesList = query.list();
                ArrayList<CompanyProfile> companies = new ArrayList<>();
                for(Object[] row:companiesList){
                    CompanyProfile company = new CompanyProfile();
                   if(row[0]!=null) company.setCompanyID((int) row[0]);
                   if(row[1]!=null)company.setCompanyName((String) row[1]);
                   if(row[2]!=null)company.setCompanyNoOfEmp(Integer.parseInt((String) row[2]));
                   if(row[3]!=null)company.setCompanyAreaKnowledge((String) row[3]);
                   if(row[4]!=null)company.setCompanyWebSite((String) row[4]);
                   if(row[5]!=null)company.setCompanyAddress((String) row[5]);
                   if(row[6]!=null) company.setCompanyPhone((String) row[6]);
                   if(row[7]!=null)company.setCompanyMobile((String) row[7]);
                   if(row[8]!=null)company.setCompanyEmail((String) row[8]);
                   if(row[9]!=null)company.setCompanyLogoPath((String) row[9]);
                   if(row[10]!=null)company.setCompanyProfilePath((String) row[10]);                
                    companies.add(company); 
                }
                return companies;
            }
        });
}
}





