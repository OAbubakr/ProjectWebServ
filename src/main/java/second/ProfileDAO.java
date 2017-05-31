/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import dto.Response;
import dto.UserData;
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
public class ProfileDAO {

    private static final String STATUSSUCCESS = "success";
    private static final String NOERROR = "";
    private static final String STATUSFAIL = "fail";
    private static final String ERROR = "server down";
    private String userTypeProcedure;
    private HibernateTemplate template;

    public ProfileDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public Response getData(int userType, int userId) {
        switch (userType) {
            case 1://login as a student
                userTypeProcedure = "GetStudentDetails";
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
        final int userIdValue = userId;
        return template.execute(new HibernateCallback<Response>() {
            boolean isCorrect = false;
            Response response = new Response();
            UserData userData = new UserData();

            /*check from db the userID from the table much the userType
            //if userID found then isCorrect = true
             */
            @Override
            public Response doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery("{CALL " + userTypeProcedure + "(:StudentID)}")
                        .setParameter("StudentID", userIdValue);
                List<Object[]> userDataValue = query.list();
                if (userDataValue.size() > 0) {
                    isCorrect = true;
                    userData.setIntakeId((int) userDataValue.get(0)[1]);
                    userData.setName((String) userDataValue.get(0)[6]);
                    userData.setImagePath((String) userDataValue.get(0)[21]);
                    userData.setTrackName((String) userDataValue.get(0)[4]);
                    Query queryBranch = sn.createSQLQuery("{CALL GetBranchByID (:BranchID)}")
                        .setParameter("BranchID", userDataValue.get(0)[3]);
                    List<Object[]> branchData = queryBranch.list();
                    if(branchData.size()>0)
                        userData.setBranchName((String) branchData.get(0)[1]);
                    //add professional data
                    /*
                    Query queryProfessional = sn.createSQLQuery("{CALL GetStudentsProfessionalByID (:studentID)}")
                        .setParameter("studentID", userIdValue);
                    List<Object[]> professionalData = queryProfessional.list();
                    if(professionalData.size()>0)
                        userData.setProfessionalData(professionalData);                    
                    */
                    
                }

                if (isCorrect) {
                    response.setResponseData((UserData)userData);
                    response.setStatus(STATUSSUCCESS);
                    response.setError(NOERROR);
                } else {
                    response.setResponseData(null);
                    response.setStatus(STATUSFAIL);
                    response.setError(ERROR);
                }
                System.out.println("response: " + response);
                return response;
            }

        });
    }

    public Response setData(int userType, int userId, UserData userData) {
        return null;
    }
}
