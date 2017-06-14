/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package second;

import bean.GraduateBasicData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 *
 * @author engra
 */
public class AllGraduateByTrackDao {

    private HibernateTemplate template;

    public AllGraduateByTrackDao() {
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public ArrayList<GraduateBasicData> getAllGraduateDataByTrackId(final int intakeId, final int platformIntakeId) {
        return template.execute(new HibernateCallback<ArrayList>() {
            @Override
            public ArrayList doInHibernate(Session sn) throws HibernateException, SQLException {

                System.out.println("*******************");

                Query query = sn.createSQLQuery(
                        " { CALL graduatePerTrackAndIntake(:intakeId,:platformIntakeId) }")
                        .setParameter("intakeId", intakeId)
                        .setParameter("platformIntakeId", platformIntakeId);

                List<Object[]> list = query.list();
                ArrayList<GraduateBasicData> graduates = new ArrayList<>();
                for (Object[] row : list) {
                    GraduateBasicData st = new GraduateBasicData();
                    st.setGraduateId((int) row[0]);
                    st.setEnglishname((String) row[1]);
                    graduates.add(st);
                }

                return graduates;
            }
        });

    }
}
