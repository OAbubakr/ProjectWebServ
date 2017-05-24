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
public class StuGrade_Instance {
    private static StuGrade_Instance stuGrade_Instance;
    private StudentGradeDAO studentGradeDAO;
    
    private StuGrade_Instance(){
        ApplicationContext factory = new FileSystemXmlApplicationContext("C:\\Users\\engra\\OneDrive\\Documents\\NetBeansProjects\\ProjectWerServ\\src\\main\\java\\second\\bean.xml");
        studentGradeDAO = factory.getBean("StudentGradeDAO", StudentGradeDAO.class);
    }
    public static StuGrade_Instance getInstance() {
        StuGrade_Instance singleInstance = StuGrade_Instance.stuGrade_Instance;
        if (singleInstance == null) {
            synchronized (DaoInstance.class) {
                if (singleInstance == null) {
                    singleInstance = new StuGrade_Instance();
                }
            }
        }
        return singleInstance;
    }
    public StudentGradeDAO getCourseDao() {
        return studentGradeDAO;
    }
}
