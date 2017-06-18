/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.Permission;
import dto.Response;
import dto.UserData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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

        Boolean ret = false;

        final String sql = "{ CALL AddPermission(:PermissionDate, :FromH, :ToH, :ToMin, :FromMin,"
                + ":EmpID, :Comment, :NumOfHours, :NumOfMinutes, :CreationDate, :LevelID, :CreatorID, "
                + ":CreationTime, :PerMon, :PerYr,:status) }";
        System.out.println("******************" + permission.getComment());

        ret = template.execute(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session sn) throws HibernateException, SQLException {
                Query query = sn.createSQLQuery(sql)
                        .setParameter("PermissionDate", permission.getPermissionDate())
                        .setParameter("FromH", String.valueOf(permission.getFromH()))
                        .setParameter("ToH", String.valueOf(permission.getToH()))
                        .setParameter("ToMin", String.valueOf(permission.getToMin()))
                        .setParameter("FromMin", String.valueOf(permission.getFromMin()))
                        .setParameter("EmpID", String.valueOf(permission.getEmpID()))
                        .setParameter("Comment", permission.getComment())
                        .setParameter("NumOfHours", String.valueOf(permission.getNumOfHours()))
                        .setParameter("NumOfMinutes", String.valueOf(permission.getNumOfMinutes()))
                        .setParameter("CreationDate", permission.getCreationDate())
                        .setParameter("LevelID", String.valueOf(permission.getLevelID()))
                        .setParameter("CreatorID", String.valueOf(permission.getCreatorID()))
                        .setParameter("CreationTime", permission.getCreationTime())
                        .setParameter("PerMon", String.valueOf(permission.getPerMon()))
                        .setParameter("PerYr", String.valueOf(permission.getPerYr())).setParameter("status", permission.getPermissionStatus());

                query.executeUpdate();
                return true;
            }
        });
        try {
            FCMNotification.sendNotification(FCMNotification.PERMISSION, "Permission Request", createPrmissionRequest(permission), "staff_" + permission.getEmpID());

        } catch (Exception ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return ret;
    }

    private String createPrmissionRequest(Permission permission) {
        return "Student: " + permission.getStudentName()
                + "\nhas requested permission on " + permission.getPermissionDate() + " from " + permission.getFromH() + ":" + permission.getFromMin() + " to "
                + permission.getToH() + ":" + permission.getToMin() + "\nCause: " + permission.getComment();

    }

    public List<Permission> getStudentPermissions(final int id) {

        return template.execute(new HibernateCallback< List<Permission>>() {

            @Override
            public List<Permission> doInHibernate(Session sn) throws HibernateException, SQLException {
                List<Permission> ret = new ArrayList<>();

                Query query = sn.createSQLQuery("{CALL  GetPermissionStudentByID (:studentID)}")
                        .setParameter("studentID", id);
                List<Object[]> permissions = query.list();

                for (Object[] row : permissions) {

                    Permission permission = new Permission();

                    Timestamp time = (Timestamp) row[1];

                    permission.setPermissionDate(time.toString());
                    permission.setFromH((int) row[2]);
                    permission.setToH((int) row[3]);
                    permission.setFromMin((int) row[4]);
                    permission.setToMin((int) row[5]);
                    permission.setComment((String) row[12]);
                    permission.setPermissionStatus((String) row[23]);

                    ret.add(permission);

                }
                return ret;
            }
        });

    }

    public List<Permission> getSupervisorPermissions(final int id) {

        return template.execute(new HibernateCallback< List<Permission>>() {

            @Override
            public List<Permission> doInHibernate(Session sn) throws HibernateException, SQLException {
                List<Permission> ret = new ArrayList<>();

                Query query = sn.createSQLQuery("{CALL  GetPermissionSupervisorByID (:EmpID ,:StartDate,:EndDate )}")
                        .setParameter("EmpID", id).setParameter("StartDate", "2016-09-01").setParameter("EndDate", "2017-06-30");

                List<Object[]> permissions = query.list();

                for (Object[] row : permissions) {

                    Permission permission = new Permission();

                    permission.setPermissionId((int) row[0]);

                    Timestamp time = (Timestamp) row[1];
//                    time.
                    permission.setPermissionDate(time.toString());
                    permission.setFromH((int) row[2]);
                    permission.setToH((int) row[3]);
                    permission.setFromMin((int) row[4]);
                    permission.setToMin((int) row[5]);
                    permission.setComment((String) row[12]);
                    permission.setPermissionStatus((String) row[23]);
                    permission.setCreatorID((int) row[15]);

                    query = sn.createSQLQuery("{CALL  GetStudentDetails (:StudentID  )}")
                            .setParameter("StudentID", (int) row[15]);
                    List<Object[]> userDataValue = query.list();

                    permission.setStudentName((String) userDataValue.get(0)[6]);

                    ret.add(permission);

                }
                return ret;
            }
        });

    }

    public void setPermssionResponse(final int permssionId, final boolean response) {

        template.execute(new HibernateCallback<Integer>() {

            @Override
            public Integer doInHibernate(Session sn) throws HibernateException, SQLException {

                String status;
                if (response) {
                    status = "Accepted";
                } else {
                    status = "Refused";
                }

                Query query = sn.createSQLQuery("{CALL  ResponsePremission (:permissionID, :newStatus)}").setParameter("permissionID", permssionId)
                        .setParameter("newStatus", status);
                query.executeUpdate();
                return 1;
            }
        });

    }

}
