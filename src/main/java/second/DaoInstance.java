/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import beans.Users;
import java.math.BigDecimal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author omari
 */
public class DaoInstance {

    private DAO dao;
    private static DaoInstance singleInstance;

    private DaoInstance() {
        // TODO code application logic here

        ApplicationContext factory = new FileSystemXmlApplicationContext("C:\\Users\\omari\\Desktop\\project\\restfulSpring\\src\\main\\java\\second\\bean.xml");

        dao = factory.getBean("DAO", DAO.class);

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

    public DAO getDAO() {
        return dao;
    }

}
