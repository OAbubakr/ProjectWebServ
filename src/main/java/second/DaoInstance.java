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

//    private StudentDAO studentDao;
//    private ProgramDAO programDAO;
    private static DaoInstance singleInstance;
//    private StudentScheduleDao studentScheduleDao;
//    private LoginDAO loginDao;
    
     ApplicationContext factory = null;

    private DaoInstance() {
        // TODO code application logic here

        factory = new FileSystemXmlApplicationContext("F:\\ITI- MWD - intake 37\\graduation project\\Final Project Java\\ProjectWerServ\\src\\main\\java\\second\\bean.xml");

//        studentDao = factory.getBean("StudentDAO", StudentDAO.class);
//        programDAO = factory.getBean("ProgramDAO", ProgramDAO.class);
//        studentScheduleDao = factory.getBean("StudentScheduleDao", StudentScheduleDao.class);
//        loginDao = factory.getBean("LoginDAO", LoginDAO.class);

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

    public ProgramDAO getProgramDAO() {
        return factory.getBean("ProgramDAO", ProgramDAO.class);
    }

    public LoginDAO getLoginDao() {
        return factory.getBean("LoginDAO", LoginDAO.class);
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

    public PostJobDAO getPostJopDAO() {
        return  factory.getBean("PostJopDAO",PostJobDAO.class);
    }
}

