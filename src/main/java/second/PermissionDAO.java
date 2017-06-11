/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.Permission;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import notifications.FCMNotification;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author Rana Gamal
 */
public class PermissionDAO {

    private HibernateTemplate template;

    public PermissionDAO() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public boolean addPermission(final Permission permission) {

//        final String sql = "{ CALL AddPermission(:PermissionDate, :FromH, :ToH, :ToMin, :FromMin,"
//                + ":EmpID, :Comment, :NumOfHours, :NumOfMinutes, :CreationDate, :LevelID, :CreatorID, "
//                + ":CreationTime, :PerMon, :PerYr) }";
//        System.out.println("******************" + permission.getComment());
//
//        template.execute(new HibernateCallback<Object>() {
//            @Override
//            public Object doInHibernate(Session sn) throws HibernateException, SQLException {
//                Query query = sn.createSQLQuery(sql)
//                        .setParameter("PermissionDate", permission.getPermissionDate())
//                        .setParameter("FromH", String.valueOf(permission.getFromH()))
//                        .setParameter("ToH", String.valueOf(permission.getToH()))
//                        .setParameter("ToMin", String.valueOf(permission.getToMin()))
//                        .setParameter("FromMin", String.valueOf(permission.getFromMin()))
//                        .setParameter("EmpID", String.valueOf(permission.getEmpID()))
//                        .setParameter("Comment", permission.getComment())
//                        .setParameter("NumOfHours", String.valueOf(permission.getNumOfHours()))
//                        .setParameter("NumOfMinutes", String.valueOf(permission.getNumOfMinutes()))
//                        .setParameter("CreationDate", permission.getCreationDate())
//                        .setParameter("LevelID", String.valueOf(permission.getLevelID()))
//                        .setParameter("CreatorID", String.valueOf(permission.getCreatorID()))
//                        .setParameter("CreationTime", permission.getCreationTime())
//                        .setParameter("PerMon", String.valueOf(permission.getPerMon()))
//                        .setParameter("PerYr", String.valueOf(permission.getPerYr()));
//
//                query.executeUpdate();
//                return "Insertion Done";
//            }
//        });
        try {
            return FCMNotification.sendNotification(FCMNotification.PERMISSION, "Permission Request", createPrmissionRequest(permission), "staff_" + permission.getEmpID());
       
        } catch (Exception ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    private String createPrmissionRequest(Permission permission){
        return "Student: " + permission.getStudentName()
              +"\nhas requested permission on " + permission.getPermissionDate() +" from "+ permission.getFromH()+":"+permission.getFromMin()+" to "
                +permission.getToH()+":"+permission.getToMin()+"\nCause: "+permission.getComment();
    
    }

}
