/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

//import beans.Users;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author omari
 */
public class DaoInstance {

    private static DaoInstance singleInstance;
     ApplicationContext factory = null;

    private DaoInstance() {
        // TODO code application logic here

         factory = new FileSystemXmlApplicationContext("C:\\Users\\omari\\Desktop\\project\\ProjectWerServ\\src\\main\\java\\second\\bean.xml");


    }

    
    public static DaoInstance getInstance() {
        DaoInstance singleInstance = DaoInstance.singleInstance;

        if (singleInstance == null) {
            synchronized (DaoInstance.class) {
                if (singleInstance == null) {
                    singleInstance = new DaoInstance();
                }
            }

        }

        return singleInstance;
    }

    public StudentDAO getStudentDAO() {
        return factory.getBean("StudentDAO", StudentDAO.class);
    }

    public StudentScheduleDao getStudentScheduleDao() {
        return factory.getBean("StudentScheduleDao", StudentScheduleDao.class);
    }
    
    public TrackScheduleDao getTrackScheduleDao() {
        return factory.getBean("TrackScheduleDao", TrackScheduleDao.class);
    }
    

    public ProgramDAO getProgramDAO() {
        return factory.getBean("ProgramDAO", ProgramDAO.class);
    }

    public LoginDAO getLoginDao() {
        return factory.getBean("LoginDAO", LoginDAO.class);
    }

    public AllStudentByTrackDao getAllStudentByTrackDao() {
        return  factory.getBean("allStudentByTrackDao",AllStudentByTrackDao.class);
    }

    public EventDAO getEventDAO() {
        return factory.getBean("eventDAO",EventDAO.class);
    }
    
    public CourseDAO getCourseDao() {
        return factory.getBean("CourseDAO", CourseDAO.class);
    }
    
    public EmpHoursDAO getEmpHours() {
        return factory.getBean("EmpHours", EmpHoursDAO.class);
    }
        
    public StudentGradeDAO getGrades() {
        return  factory.getBean("StudentGradeDAO", StudentGradeDAO.class);
    }

    public PostJobDAO getPostJobDAO() {
        return  factory.getBean("PostJobDAO",PostJobDAO.class);
    }
    
    public InstructorsByBranchDAO getInstructorsByBranchDAO() {
        return  factory.getBean("InstructorsByBranchDAO",InstructorsByBranchDAO.class);
    }
    public CompanyDAO getCompanyDAO() {
        return factory.getBean("CompanyDAO",CompanyDAO.class);
    }
    

    public InstructorScheduleDao getInstructorScheduleDao() {
        return  factory.getBean("InstructorScheduleDao",InstructorScheduleDao.class);
    }
    
    public PermissionDAO getPermissionDAO() {
        return  factory.getBean("PermissionDAO",PermissionDAO.class);
    }
    public ProfileDAO getProfileDao(){
        return factory.getBean("ProfileDAO",ProfileDAO.class);
    }
    
    public InstructorEvaluationDAO getInstructorEvaluationDAO(){
        return factory.getBean("InstructorEvaluationDAO",InstructorEvaluationDAO.class);
    }
    public SaveImageDao getSaveImageDao(){
        return factory.getBean("SaveImageDao",SaveImageDao.class);
    }
    
    
}

