/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author engra
 */
public class EmpHours_DAOInstance {

    private static EmpHours_DAOInstance empHours_DAOInstance;
    private EmpHoursDAO empHoursDAO;

    private EmpHours_DAOInstance() {
        ApplicationContext factory = new FileSystemXmlApplicationContext("C:\\Users\\engra\\OneDrive\\Documents\\NetBeansProjects\\ProjectWerServ\\src\\main\\java\\second\\bean.xml");
        empHoursDAO = factory.getBean("EmpHours", EmpHoursDAO.class);
    }
    
    public static EmpHours_DAOInstance getInstance() {
        EmpHours_DAOInstance singleInstance = EmpHours_DAOInstance.empHours_DAOInstance;
        if (singleInstance == null) {
            synchronized (DaoInstance.class) {
                if (singleInstance == null) {
                    singleInstance = new EmpHours_DAOInstance();
                }
            }
        }
        return singleInstance;
    }
    public EmpHoursDAO getEmpHours() {
        return empHoursDAO;
    }
}
