/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import dto.LoginResponse;
import dto.Response;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Mahmoud
 */
public class LoginDAO {

    private static final String STATUSSUCCESS = "success";
    private static final String NOERROR = "";
    private static final String STATUSFAIL = "fail";
    private static final String ERROR = "invalid username or password";
    private String userTypeProcedure;
    private HibernateTemplate template;

    public LoginDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public Response getUserId(int userType, String userName, String password) {

        switch (userType) {
            case 1://login as a student
                userTypeProcedure = "StudentDataLogin";
                break;
            case 2://login as a staff
                userTypeProcedure = "";
                break;
            case 3://login as a company
                userTypeProcedure = "";
                break;
            case 4://login as a graduate
                userTypeProcedure = "";
                break;
        }
        final String userNameValue = userName;
        final String passwordValue = password;
        return template.execute(new HibernateCallback<Response>() {
            boolean isCorrect = false;
            Response response = new Response();
            int userId = 0;
            /*check from db the username and password from the table much the userType
            //if username found then isCorrectUserName = true
            //if password found then isCorrectPassword = true
             */
            @Override
            public Response doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery("{CALL " + userTypeProcedure + "(:userName,password)}")
                        .setParameter("userName", userNameValue)
                        .setParameter("password", passwordValue);
                List idValue = query.list();
                if(idValue.size()>0){
                    isCorrect = true;
                    userId = (int) idValue.get(0);
                }
                
                if (isCorrect) {
                    response.setResponseData(userId);
                    response.setStatus(STATUSSUCCESS);
                    response.setError(NOERROR);
                } else {
                    response.setResponseData(userId);
                    response.setStatus(STATUSFAIL);
                    response.setError(ERROR);
                }
                System.out.println("response: " + response);
                return response;
            }

        });
//        if (criteriaClass != null) {
//            System.out.println(userType + " before database");
//            List checkUserName = template.findByCriteria(DetachedCriteria.forClass(criteriaClass)
//                    .add(Restrictions.eq("username", userName))
//                    .setProjection(Projections.id()));
//            if (checkUserName.size() > 0) {
//                isCorrectUserName = true;
//                List idValue = template.findByCriteria(DetachedCriteria.forClass(StudentBasicData.class)
//                        .add(Restrictions.and(Restrictions.eq("username", userName), Restrictions.eq("userpwd", password)))
//                        .setProjection(Projections.id()));
//                if(idValue.size()>0){
//                    isCorrectPassword = true;
//                    userId = (int) idValue.get(0);
//                }
//            }
//        }
        ///////////////

    }
}
