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

    private StudentDAO studentDao;
    private ProgramDAO programDAO;
    private static DaoInstance singleInstance;
    private StudentScheduleDao studentScheduleDao;
    private LoginDAO loginDao;
    private PostJobDAO postJobDAO;

    private DaoInstance() {
        // TODO code application logic here

        ApplicationContext factory = new FileSystemXmlApplicationContext("D:\\ITI_GRADUATION\\ProjectWerServ\\src\\main\\java\\second\\bean.xml");

        studentDao = factory.getBean("StudentDAO", StudentDAO.class);
        programDAO = factory.getBean("ProgramDAO", ProgramDAO.class);
        studentScheduleDao = factory.getBean("StudentScheduleDao", StudentScheduleDao.class);
        loginDao = factory.getBean("LoginDAO", LoginDAO.class);
        postJobDAO = factory.getBean("PostJobDAO", PostJobDAO.class);

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
        return studentDao;
    }

    public StudentScheduleDao getStudentScheduleDao() {
        return studentScheduleDao;
    }

    public ProgramDAO getProgramDAO() {
        return programDAO;
    }

    public LoginDAO getLoginDao() {
        return loginDao;
    }

    public PostJobDAO getPostJobDAO() {
        return postJobDAO;
    }
}

