/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.StudentDataByTrackID;
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
public class AllStudentByTrackDao {
    
    private HibernateTemplate template;

    public AllStudentByTrackDao() {
    }

    
    
    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    public ArrayList<StudentDataByTrackID> getAllStudentsDataByTrackId(final int id){
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {
                String idString = String.valueOf(id);
               Query query =sn.createSQLQuery("{ CALL GetStudentByTrackID(:PlatformIntakeID) }")
                       .setParameter("PlatformIntakeID",id);
               
           
               
           List<Object[]> list = query.list();
                ArrayList<StudentDataByTrackID> students = new ArrayList<>();
                for(Object[]row:list){
                  StudentDataByTrackID st =new StudentDataByTrackID();
                  st.setStudentId((Integer)row[0]);
                  st.setEnglishname((String)row[1]);
//                  st.setArabicname((String)row[2]);
//                  st.setApplicationNo((String)row[3]);
//                  st.setFkFacultyId((Integer)row[4]);
//                  st.setFkSpecializationId((Integer)row[5]);
//                  st.setFkGraduationGradeId((Integer)row[6]);
////                  st.setGraduationYear((String)row[7]);
//                  st.setPhone((String)row[8]);
//                  st.setMobile((String)row[9]);
//                  st.setEmail((String)row[10]);
//                  st.setSubTrackId((Integer)row[11]);
//                  st.setBarcode((String)row[12]);
//                  st.setAccFlag((Integer)row[13]);
//                  st.setUsername((String)row[14]);
//                  st.setUserpwd((String)row[15]);
//                  st.setImagepath((String)row[16]);
//                  st.setRejectReason((String)row[17]);
////                  st.setDateOb((Date)row[18]);
//                  st.setGender((String)row[19]);
//                  st.setSubTrackName((String)row[20]);
//                  st.setMarital((Integer)row[21]);
//                  st.setMilitary((Integer)row[22]);
//                  st.setLanguage((Integer)row[23]);
//                  st.setSocialAccount((String)row[24]);
//                  st.setSchool((String)row[25]);
//                  st.setGradProjIdea((String)row[26]);
//                  st.setProjGrade((String)row[27]);
//                  st.setCertAqui((String)row[28]);
//                  st.setPostGradStud((String)row[29]);
//                  st.setAcadimicInst((String)row[30]);
//                  st.setBranchId((Integer)row[32]);
//                  st.setProgramIntakeId((Integer)row[33]);
//                  st.setAddress((String)row[34]);
//                  st.setIdno((String)row[35]);
//                  st.setGraduationGrade((String)row[36]);
//                  st.setCvpath((String)row[37]);
//                  st.setSchoolName((String)row[38]);
                          
                  
                          
                  
                         
                         
                          
                          
                         
                  
                  
                  students.add(st);
                }
                
                return students;
            }
        });
        
        
    }
    
    
    
}
