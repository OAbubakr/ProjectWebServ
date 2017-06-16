/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.Course;
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
public class SaveGraduateImageDao {
    private HibernateTemplate template;

    public SaveGraduateImageDao() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    
    public String insertImage(final int id , final String imagePath){
        return (String) template.execute(new HibernateCallback<String>() {
            @Override
            public String doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(
                        " { CALL setGraduateImage(:graduateId,:imagepath) }")
                        .setParameter("graduateId", id)
                        .setParameter("imagepath", imagePath);
                 query.executeUpdate();
                return "Done";
            }
        });
    }
}
