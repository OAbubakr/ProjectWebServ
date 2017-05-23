/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.StudentBasicData;
import dto.LoginResponse;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Mahmoud
 */
public class LoginDAO {

    private static final String STATUSSUCCESS = "success";
    private static final String NOERROR = "";
    private static final String STATUSFAIL = "fail";
    private static final String ERRORPASS = "incorrect password";
    private static final String ERRORUSERNAME = "incorrect username";
    private HibernateTemplate template;

    public LoginDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public LoginResponse getUserId(int userType, String userName, String password) {
        
        boolean isCorrectUserName = false;
        boolean isCorrectPassword = false;
        LoginResponse response = new LoginResponse();
        int userId = 0;
        Class criteriaClass = null;
        /*check from db the username and password from the table much the userType
        //if username found then isCorrectUserName = true
        //if password found then isCorrectPassword = true
         */
        switch (userType) {
            case 1:
                criteriaClass = StudentBasicData.class;
                break;
        }
        if (criteriaClass != null) {
            System.out.println(userType + " before database");
            List checkUserName = template.findByCriteria(DetachedCriteria.forClass(criteriaClass)
                    .add(Restrictions.eq("username", userName))
                    .setProjection(Projections.id()));
            if (checkUserName.size() > 0) {
                isCorrectUserName = true;
                List idValue = template.findByCriteria(DetachedCriteria.forClass(StudentBasicData.class)
                        .add(Restrictions.and(Restrictions.eq("username", userName), Restrictions.eq("userpwd", password)))
                        .setProjection(Projections.id()));
                if(idValue.size()>0){
                    isCorrectPassword = true;
                    userId = (int) idValue.get(0);
                }
            }
        }
        ///////////////
        if (isCorrectPassword && isCorrectUserName) {
            response.setData(userId);
            response.setStatus(STATUSSUCCESS);
            response.setError(NOERROR);
        } else if (!isCorrectUserName) {
            response.setData(userId);
            response.setStatus(STATUSFAIL);
            response.setError(ERRORUSERNAME);
        } else {
            response.setData(userId);
            response.setStatus(STATUSFAIL);
            response.setError(ERRORPASS);
        }
        System.out.println("response: "+response);
        return response;
    }
}
