/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.CompanyProfile;
import dto.Response;
import dto.UserData;
import java.sql.SQLException;
import java.util.Calendar;
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

    private static final String STATUSSUCCESS = "SUCCESS";
    private static final String NOERROR = "";
    private static final String STATUSFAIL = "FAILURE";
    private static final String ERROR = "server down";
    private String userTypeProcedure;
    private String userIdProcedure;
    private HibernateTemplate template;

    public ProfileDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public Response getData(int userId, int userType) {
        switch (userType) {
            case 1://login as a student
                userTypeProcedure = "GetStudentDetails";
                userIdProcedure = "StudentID";
                break;
            case 2://login as a staff
                userTypeProcedure = "GetEmpByID";
                userIdProcedure = "EmployeeID";
                break;
            case 3://login as a company
                userTypeProcedure = "GetCompanyProfileByCompanyID";
                userIdProcedure = "CompanyID";
                break;
            case 4://login as a graduate
                userTypeProcedure = "GetGraduateDetails";
                userIdProcedure = "graduateId";
                break;
        }
        final int userTypeValue = userType;
        
        final int userIdValue = new Integer(userId);
        return template.execute(new HibernateCallback<Response>() {
            boolean isCorrect = false;
            Response response = new Response();
            UserData userData = new UserData();

            /*check from db the userID from the table much the userType
            //if userID found then isCorrect = true
             */
            @Override
            public Response doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery("{CALL " + userTypeProcedure + "(:" + userIdProcedure + ")}")
                        .setParameter(userIdProcedure, userIdValue);
                List<Object[]> userDataValue = query.list();
                if (userDataValue.size() > 0) {
                    isCorrect = true;
                    switch (userTypeValue) {
                        case 1://login as a student
                            userData = prepareStudentData(sn, userDataValue, userIdValue);
                            break;
                        case 2://login as a staff
                            userData = prepareStaffData(sn, userDataValue, userIdValue);
                            break;
                        case 3://login as a company
                            userData = prepareCompanyData(sn, userDataValue);
                            break;
                        case 4://login as a graduate
                            userData = prepareGraduateData(sn, userDataValue, userIdValue);
                            break;
                    }

                }

                if (isCorrect) {
                    response.setResponseData((UserData) userData);
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

        final int userTypeValue = userType;
        final int userIdValue = userId;
        final UserData userDataInfo = userData;

        return template.execute(new HibernateCallback<Response>() {

            /*check from db the userID from the table much the userType
            //if userID found then isCorrect = true
             */
            @Override
            public Response doInHibernate(Session sn) throws HibernateException, SQLException {
                switch (userTypeValue) {
                    case 1://login as a student
                        addStudentData(sn, userIdValue, userDataInfo);
                        break;
                    case 2://login as a staff

                        break;
                    case 3://login as a company

                        break;
                    case 4://login as a graduate
                        addGraduateData(sn, userIdValue, userDataInfo);
                        break;
                }
                Response response = new Response();
                response.createResponse("EditData");
                return response;
            }

        });
    }

    private void addStudentData(Session sn, int userIdValue, UserData userDataInfo) {
        Query query = sn.createSQLQuery("{CALL AddStudentProfessionals(:StudentID,:gitUrl,:behanceUrl,:linkedInUrl)}")
                .setParameter("StudentID", userIdValue)
                .setParameter("gitUrl", userDataInfo.getGitUrl())
                .setParameter("behanceUrl", userDataInfo.getBehanceUrl())
                .setParameter("linkedInUrl", userDataInfo.getLinkedInUrl());
        query.executeUpdate();

        query = sn.createSQLQuery("{CALL EditStudentData(:StudentID,:Mobile,:Email)}")
                .setParameter("StudentID", userIdValue)
                .setParameter("Mobile", userDataInfo.getStudentMobile())
                .setParameter("Email", userDataInfo.getStudentEmail());
        query.executeUpdate();
    }

    private void addGraduateData(Session sn, int userIdValue, UserData userDataInfo) {
        Query query = sn.createSQLQuery("{CALL AddGraduateProfessionals(:graduateID,:gitUrl,:behanceUrl,:linkedInUrl)}")
                .setParameter("graduateID", userIdValue)
                .setParameter("gitUrl", userDataInfo.getGitUrl())
                .setParameter("behanceUrl", userDataInfo.getBehanceUrl())
                .setParameter("linkedInUrl", userDataInfo.getLinkedInUrl());
        query.executeUpdate();

        query = sn.createSQLQuery("{CALL EditGraduateData(:graduateID,:Mobile,:Email)}")
                .setParameter("graduateID", userIdValue)
                .setParameter("Mobile", userDataInfo.getStudentMobile())
                .setParameter("Email", userDataInfo.getStudentEmail());
        query.executeUpdate();
    }

    private UserData prepareStudentData(Session sn, List<Object[]> userDataValue, int userIdValue) {
        UserData userData = new UserData();

        
        userData.setPlatformIntakeId((int) userDataValue.get(0)[2]);
        userData.setId((int) userDataValue.get(0)[5]);
        userData.setName((String) userDataValue.get(0)[6]);
        userData.setImagePath((String) userDataValue.get(0)[21]);
        userData.setTrackName((String) userDataValue.get(0)[4]);
        userData.setStudentEmail((String) userDataValue.get(0)[15]);
        userData.setStudentMobile((String) userDataValue.get(0)[14]);
        Query queryBranch = sn.createSQLQuery("{CALL GetBranchByID (:BranchID)}")
                .setParameter("BranchID", userDataValue.get(0)[3]);
        List<Object[]> branchData = queryBranch.list();
        if (branchData.size() > 0) {
            userData.setBranchName((String) branchData.get(0)[1]);
        }
        Query queryIntakeId = sn.createSQLQuery("{CALL GetIntakeData (:ProgramID,:ProgramIntakeID)}")
                .setParameter("ProgramID", (int) userDataValue.get(0)[28])
                .setParameter("ProgramIntakeID", (int) userDataValue.get(0)[1]);
        List<Object[]> intakeIdData = queryIntakeId.list();
        if (intakeIdData.size() > 0) {
            userData.setIntakeId((int) intakeIdData.get(0)[0]);
            userData.setIntakeName((String) intakeIdData.get(0)[1]);
        }
        //add professional data

        Query queryProfessional = sn.createSQLQuery("{CALL GetStudentsProfessionalByID (:studentID)}")
                .setParameter("studentID", userIdValue);
        List<Object[]> professionalData = queryProfessional.list();
        if (professionalData.size() > 0) {
            userData.setGitUrl((String) professionalData.get(0)[0]);
            userData.setBehanceUrl((String) professionalData.get(0)[1]);
            userData.setLinkedInUrl((String) professionalData.get(0)[2]);
        }
        return userData;
    }

    private UserData prepareCompanyData(Session sn, List<Object[]> companyList) {
        UserData companyProfile = new UserData();

        for (Object[] cObject : companyList) {

            if (cObject[0] != null) {
                companyProfile.setId((int) cObject[0]);
            }
            if (cObject[1] != null) {
                companyProfile.setCompanyName((String) cObject[1]);
            }
            if (cObject[2] != null) {
                companyProfile.setCompanyNoOfEmp(Integer.parseInt((String) cObject[2]));
            }
            if (cObject[3] != null) {
                companyProfile.setCompanyAreaKnowledge((String) cObject[3]);
            }
            if (cObject[4] != null) {
                companyProfile.setCompanyWebSite((String) cObject[4]);
            }
            if (cObject[5] != null) {
                companyProfile.setCompanyAddress((String) cObject[5]);
            }
            if (cObject[6] != null) {
                companyProfile.setCompanyPhone((String) cObject[6]);
            }
            if (cObject[7] != null) {
                companyProfile.setCompanyMobile((String) cObject[7]);
            }
            if (cObject[8] != null) {
                companyProfile.setCompanyEmail((String) cObject[8]);
            }
            if (cObject[9] != null) {
                companyProfile.setCompanyLogoPath((String) cObject[9]);
            }
            if (cObject[10] != null) {
                companyProfile.setCompanyProfilePath((String) cObject[10]);
            }
            if (cObject[13] != null) {
                companyProfile.setCompanyUserName((String) cObject[13]);
            }
            if (cObject[14] != null) {
                companyProfile.setCompanyPassWord((String) cObject[14]);
            }
        }

        return companyProfile;
    }

    private UserData prepareStaffData(Session sn, List<Object[]> userDataValue, int userIdValue) {
        UserData userData = new UserData();
        userData.setId((int) userDataValue.get(0)[0]);
        userData.setEmployeeName((String) userDataValue.get(0)[1]);
        userData.setEmployeePosition((String) userDataValue.get(0)[41]);
        userData.setEmployeeBranchId((int) userDataValue.get(0)[2]);
        Query queryBranch = sn.createSQLQuery("{CALL GetBranchByID (:BranchID)}")
                .setParameter("BranchID", userDataValue.get(0)[2]);
        List<Object[]> branchData = queryBranch.list();
        if (branchData.size() > 0) {
            userData.setEmployeeBranchName((String) branchData.get(0)[1]);
        }
        int intakeId = 0;
        java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        Query queryIntake = sn.createSQLQuery("{CALL getIntakeNoByDate (:currentDate)}")
                .setParameter("currentDate", sqlDate);
        List<Integer> intakeData = queryIntake.list();
        if (intakeData.size() > 0) {
            intakeId =   intakeData.get(0);
        }
        if(intakeId > 0){
            Query querySupervisor = sn.createSQLQuery("{CALL IsSupervisor(:ProgramID,:IntakeID,:EmployeeID)}")
                    .setParameter("ProgramID", 4)//9month
                    .setParameter("IntakeID", intakeId)
                    .setParameter("EmployeeID", userIdValue);
            List<Object[]> queryData = querySupervisor.list();
            if (queryData.size() > 0) {
                if(queryData.get(0)[2] != null)
                    userData.setEmployeePlatformIntake((int) queryData.get(0)[2]);
                if(queryData.get(0)[1] != null)
                    userData.setEmployeeSubTrackId((int) queryData.get(0)[1]);
            }else{
            querySupervisor = sn.createSQLQuery("{CALL IsSupervisor(:ProgramID,:IntakeID,:EmployeeID)}")
                    .setParameter("ProgramID", 5)//nano
                    .setParameter("IntakeID", intakeId)
                    .setParameter("EmployeeID", userIdValue);
            queryData = querySupervisor.list();
            if (queryData.size() > 0) {
                if(queryData.get(0)[2] != null)
                    userData.setEmployeePlatformIntake((int) queryData.get(0)[2]);
                if(queryData.get(0)[1] != null)
                    userData.setEmployeeSubTrackId((int) queryData.get(0)[1]);
                }
            }
            Query querySubTrack = sn.createSQLQuery("{CALL GetSubtrackData(:PlatformIntakeID)}")
                    .setParameter("PlatformIntakeID", userData.getEmployeePlatformIntake());
            List<Object[]> subTrackData = querySubTrack.list();
            if (queryData.size() > 0) {
                if(subTrackData.size() > 0)
                    if(subTrackData.get(0)[0] != null)
                        userData.setEmployeeSubTrackName((String) subTrackData.get(0)[0]);
            }
        }
        return userData;
    }

    private UserData prepareGraduateData(Session sn, List<Object[]> userDataValue, int userIdValue) {
        UserData userData = new UserData();

        userData.setPlatformIntakeId((int) userDataValue.get(0)[2]);
        userData.setId((int) userDataValue.get(0)[5]);
        userData.setName((String) userDataValue.get(0)[6]);
        userData.setImagePath((String) userDataValue.get(0)[21]);
        userData.setTrackName((String) userDataValue.get(0)[4]);
        userData.setStudentEmail((String) userDataValue.get(0)[15]);
        userData.setStudentMobile((String) userDataValue.get(0)[14]);
        Query queryBranch = sn.createSQLQuery("{CALL GetBranchByID (:BranchID)}")
                .setParameter("BranchID", userDataValue.get(0)[3]);
        List<Object[]> branchData = queryBranch.list();
        if (branchData.size() > 0) {
            userData.setBranchName((String) branchData.get(0)[1]);
        }
        Query queryIntakeId = sn.createSQLQuery("{CALL GetIntakeData (:ProgramID,:ProgramIntakeID)}")
                .setParameter("ProgramID", (int) userDataValue.get(0)[1])
                .setParameter("ProgramIntakeID", (int) userDataValue.get(0)[2]);
        List<Object[]> intakeIdData = queryIntakeId.list();
        if (intakeIdData.size() > 0) {
            userData.setIntakeId((int) intakeIdData.get(0)[0]);
            userData.setIntakeName((String) intakeIdData.get(0)[1]);
        }
        //add professional data

        Query queryProfessional = sn.createSQLQuery("{CALL GetGraduatesProfessionalByID (:graduateID)}")
                .setParameter("graduateID", userIdValue);
        List<Object[]> professionalData = queryProfessional.list();
        if (professionalData.size() > 0) {
            userData.setGitUrl((String) professionalData.get(0)[0]);
            userData.setBehanceUrl((String) professionalData.get(0)[1]);
            userData.setLinkedInUrl((String) professionalData.get(0)[2]);
        }
        return userData;
    }
}
