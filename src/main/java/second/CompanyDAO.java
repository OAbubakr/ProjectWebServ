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
                    company.setCompanyID((int) row[0]);
                    company.setCompanyName((String) row[1]);
                    company.setCompanyNoOfEmp(Integer.parseInt((String) row[2]));
                    company.setCompanyAreaKnowledge((String) row[3]);
                    company.setCompanyWebSite((String) row[4]);
                    company.setCompanyAddress((String) row[5]);
                    company.setCompanyPhone((String) row[6]);
                    company.setCompanyMobile((String) row[7]);
                    company.setCompanyEmail((String) row[8]);
                    company.setCompanyLogoPath((String) row[9]);
                    company.setCompanyProfilePath((String) row[10]);
                    company.setCompanyUserName((String) row[13]);
                    company.setCompanyPassWord((String) row[14]); 
                
                    companies.add(company); 
                }
                return companies;
            }
        });
}
}





