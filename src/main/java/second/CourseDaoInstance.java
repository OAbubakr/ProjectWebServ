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
public class CourseDaoInstance {
    
    private static CourseDaoInstance courseDaoInstance;
    private CourseDAO courseDAO;
    
    private CourseDaoInstance(){
        ApplicationContext factory = new FileSystemXmlApplicationContext("C:\\Users\\engra\\OneDrive\\Documents\\NetBeansProjects\\ProjectWerServ\\src\\main\\java\\second\\bean.xml");
        courseDAO = factory.getBean("CourseDAO", CourseDAO.class);
    }
    public static CourseDaoInstance getInstance() {
        CourseDaoInstance singleInstance = CourseDaoInstance.courseDaoInstance;
        if (singleInstance == null) {
            synchronized (DaoInstance.class) {
                if (singleInstance == null) {
                    singleInstance = new CourseDaoInstance();
                }
            }
        }
        return singleInstance;
    }
    public CourseDAO getCourseDao() {
        return courseDAO;
    }
    
    
}
