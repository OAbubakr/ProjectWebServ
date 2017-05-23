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
    private static DaoInstance singleInstance;
    private StudentScheduleDao studentScheduleDao;
    private LoginDAO loginDao;

    private DaoInstance() {
        // TODO code application logic here

        ApplicationContext factory = new FileSystemXmlApplicationContext("C:\\Users\\engra\\OneDrive\\Documents\\NetBeansProjects\\ProjectWerServ\\src\\main\\java\\second\\bean.xml");

        studentDao = factory.getBean("StudentDAO", StudentDAO.class);
        studentScheduleDao = factory.getBean("StudentScheduleDao", StudentScheduleDao.class);
        loginDao = factory.getBean("LoginDAO", LoginDAO.class);
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

    public LoginDAO getLoginDao(){
        return loginDao;
    }
}
